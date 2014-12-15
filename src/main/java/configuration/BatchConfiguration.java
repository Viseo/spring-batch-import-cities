package configuration;

import bean.City;
import bean.CityCsv;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import processor.CityItemProcessor;
import tasklet.HelloWorldTasklet;


/**
 * User : Emmanuel PIERRE
 * Date: 30/05/14
 * Time: 15:02
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<CityCsv> reader() {
        FlatFileItemReader<CityCsv> reader = new FlatFileItemReader<CityCsv>();
		/* Fichier de test dans le classpath */
        final ClassPathResource resource = new ClassPathResource("allCountriesSample.txt");
		/* Fichier complet */
        final FileSystemResource fileSystemResource = new FileSystemResource("K:\\allCountries\\allCountries.txt");
        reader.setResource(resource);
        reader.setLineMapper(new DefaultLineMapper<CityCsv>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id","name","asciiname","alternatenames","latitude","longitude","featureClass","featureCode","countryCode","cc2","admin1Code",
                        "admin2Code","admin3Code","admin4Code","population","elevation","dem","timezone","modificationDate"});
                setDelimiter(DelimitedLineTokenizer.DELIMITER_TAB);
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CityCsv>() {{
                setTargetType(CityCsv.class);
            }});
        }});

        return reader;
    }

    @Bean
    public ItemProcessor<CityCsv, City> processor() {
        return new CityItemProcessor();
    }

    @Bean
    public ItemWriter<City> writer(MongoOperations template) {
        final MongoItemWriter<City> mongoItemWriter = new MongoItemWriter<City>();
        mongoItemWriter.setTemplate(template);
        mongoItemWriter.setCollection("city");
        return mongoItemWriter;
    }
    // end::readerwriterprocessor[]

    // tag::jobstep[]
    @Bean
    public Job importCitiesJob(JobBuilderFactory jobs, @Qualifier("step1")Step s1, @Qualifier("step2")Step s2) {
        return jobs.get("importCitiesJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1).next(s2)
                .end()
                .build();
    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(), "test2");
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<CityCsv> reader,
                      ItemWriter<City> writer, ItemProcessor<CityCsv, City> processor) {

        ItemReadListener<CityCsv> itemReadListener = new ItemReadListener<CityCsv>() {

            @Override
            public void beforeRead() {

            }

            @Override
            public void afterRead(CityCsv o) {

            }

            @Override
            public void onReadError(Exception e) {
                logger.warn("Error reading line : {}", e.getMessage());
            }
        };


        return stepBuilderFactory.get("step1")
                .<CityCsv, City> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(itemReadListener)
                .faultTolerant().skip(Exception.class).skipLimit(200000)
                .build();
    }

    @Bean
    public Step step2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step2").tasklet(new HelloWorldTasklet())
               .build();
    }
    // end::jobstep[]
}
