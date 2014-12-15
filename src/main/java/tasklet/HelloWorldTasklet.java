package tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class HelloWorldTasklet implements Tasklet {

    public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
            throws Exception {
        System.out.println("");
        System.out.println(" XXX XXX           XX      XX             ");
        System.out.println("  X   X             X       X             ");
        System.out.println("  X   X             X       X             ");
        System.out.println("  X   X   XXXXX     X       X     XXXXX   ");
        System.out.println("  XXXXX  X     X    X       X    X     X  ");
        System.out.println("  X   X  XXXXXXX    X       X    X     X  ");
        System.out.println("  X   X  X          X       X    X     X  ");
        System.out.println("  X   X  X     X    X       X    X     X  ");
        System.out.println(" XXX XXX  XXXXX   XXXXX   XXXXX   XXXXX   ");
        System.out.println("                                          ");
        System.out.println("                                          ");
        System.out.println("                                          ");
        System.out.println("                                          ");
        System.out.println(" XXX XXX                   XX        XX   ");
        System.out.println("  X   X                     X         X   ");
        System.out.println("  X   X                     X         X   ");
        System.out.println("  X   X   XXXXX  XXX XX     X     XXXXX   ");
        System.out.println("  X X X  X     X   XX  X    X    X    X   ");
        System.out.println("  X X X  X     X   X        X    X    X   ");
        System.out.println("  X X X  X     X   X        X    X    X   ");
        System.out.println("   X X   X     X   X        X    X    X   ");
        System.out.println("   X X    XXXXX  XXXXX    XXXXX   XXXXXX  ");
        System.out.println("");
        return RepeatStatus.FINISHED;
    }
}
