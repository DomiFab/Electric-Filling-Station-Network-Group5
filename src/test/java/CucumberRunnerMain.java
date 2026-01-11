public class CucumberRunnerMain {
    public static void main(String[] args) {
        byte exitStatus = io.cucumber.core.cli.Main.run(
                new String[]{
                        "--plugin", "pretty",
                        "--glue", "steps",
                        "src/test/resources/features"
                },
                Thread.currentThread().getContextClassLoader()
        );

        System.exit(exitStatus);
    }
}
