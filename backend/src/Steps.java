public class Steps {
    private static int steps_rendered = 0;
    private static int steps_displayed = 0;
    private static double xCenter = -10;
    private static double yCenter = -10;

    public static void setxCenter(double xCenter) {
        Steps.xCenter = xCenter;
    }

    public static void setyCenter(double yCenter) {
        Steps.yCenter = yCenter;
    }

    public static double getxCenter() {
        return xCenter;
    }

    public static double getyCenter() {
        return yCenter;
    }

    public static int getSteps_displayed() {
        return steps_displayed;
    }

    public static int getSteps_rendered() {
        return steps_rendered;
    }

    public static void setSteps_displayed(int displayed) {
        steps_displayed = displayed;
    }

    public static void setSteps_rendered(int rendered) {
        steps_rendered = rendered;
    }
}
