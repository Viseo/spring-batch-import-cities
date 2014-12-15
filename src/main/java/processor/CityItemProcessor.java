package processor;

import bean.City;
import bean.CityCsv;
import bean.Feature;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User : Emmanuel PIERRE
 * Date: 30/05/14
 * Time: 14:25
 */
public class CityItemProcessor implements ItemProcessor<CityCsv, City> {

    private SimpleDateFormat dateFormater1 = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd"); //2012-01-18
    private static Logger logger = LoggerFactory.getLogger(CityItemProcessor.class);

    @Override
    public City process(CityCsv cityCsv) throws Exception {

        final City city = new City();
        city.setId(cityCsv.getId());
        city.setName(cityCsv.getName());
        city.setAsciiname(cityCsv.getAsciiname());
        final String alternateNames = cityCsv.getAlternatenames();
        if (alternateNames != null && !alternateNames.isEmpty()) {
            city.setAlternateNames(Lists.newArrayList(alternateNames.split(",")));
        }

        city.setLatitude(Double.parseDouble(cityCsv.getLatitude()));
        city.setLongitude(Double.parseDouble(cityCsv.getLongitude()));

        final String featureClass = cityCsv.getFeatureClass();
        final String featureCode = cityCsv.getFeatureCode();
        if (StringUtils.isNotBlank(featureClass) || StringUtils.isNotBlank(featureCode)) {
            city.setFeature(new Feature(featureClass, featureCode));
        }

        city.setCountryCode(cityCsv.getCountryCode());

        final String alternateCountryCodes = cityCsv.getCc2();
        if (alternateCountryCodes != null && !alternateCountryCodes.isEmpty()) {
            city.setAlternateCountryCodes(Lists.newArrayList(alternateCountryCodes.split(",")));
        }

        city.setAdmin1Code(cityCsv.getAdmin1Code());
        city.setAdmin2Code(cityCsv.getAdmin2Code());
        city.setAdmin3Code(cityCsv.getAdmin3Code());
        city.setAdmin4Code(cityCsv.getAdmin4Code());
        city.setPopulation(cityCsv.getPopulation());
        city.setElevation(cityCsv.getElevation());
        city.setDem(cityCsv.getDem());
        city.setTimezone(cityCsv.getTimezone());
        if (StringUtils.isNotBlank(cityCsv.getModificationDate())) {
            Date modificationDate = null;
            try {
                modificationDate = dateFormater1.parse(cityCsv.getModificationDate());
            } catch (ParseException pe) {
                try {
                    modificationDate = dateFormater2.parse(cityCsv.getModificationDate());
                } catch (ParseException pe2) {
                    logger.warn("Error parsing date {} from city {}-{}", cityCsv.getModificationDate(), cityCsv.getId(), cityCsv.getName());
                }
            }
            city.setModificationDate(modificationDate);
        }

        logger.debug("Convert city {} : {}", city.getId(), city.getName());
        return city;
    }
}
