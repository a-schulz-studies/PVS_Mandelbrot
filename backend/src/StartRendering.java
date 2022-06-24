class StartRendering extends Thread{
    private int resolution = 500;
    private int count = 1;
    private boolean exitThread;
    public void stopThread() {
        exitThread = true;
    }


    public void run(){
        while(exitThread!= true) {
            if(Steps.getxCenter()== -10 || Steps.getyCenter()==-10){
                System.out.println("Waiting for Startpoint.");
            }
            else{
                if(Steps.getSteps_rendered() > Steps.getSteps_displayed() + 3){
                    System.out.println("Pre-rendering finished waiting for CLient.");
                }
                else{
                    System.out.println("Pre-rendering Step:" + Steps.getSteps_rendered());
                }
            }
            while (Steps.getSteps_rendered() > Steps.getSteps_displayed() + 3 || Steps.getxCenter()== -10 || Steps.getyCenter()==-10) {
                try {
                    this.sleep(10);
                } catch (InterruptedException e) {
                }
            }
            int numThreads = 4;
            Thread[] threads = new Thread[numThreads];
            int picsPerThread = 3;
            String fName = "mbrot";
            int maxIterations = 1000;
            ColorScheme color = new ColorScheme(maxIterations);
            for (int a = 0; a < numThreads; a++) {
                MbrotParameter[] mbrots = new MbrotParameter[picsPerThread];
                for (int b = 0; b < picsPerThread; b++) {
                    mbrots[b] = new MbrotParameter(fName + String.valueOf(count),
                            resolution,Steps.getxCenter(), Steps.getyCenter());
                    count++;
                    resolution += 100*Steps.getSteps_rendered()*Steps.getSteps_rendered();
                }
                threads[a] = new Thread(new MandelbrotRenderer(mbrots, color,
                        maxIterations));
            }

            for (Thread t : threads) {
                t.start();
            }
            for (Thread t : threads) {
                try {
                    t.join();
                } catch (Exception e) {
                }
            }
            Steps.setSteps_rendered(Steps.getSteps_rendered()+1);
        }
        System.out.println("Thread beendet");
    }
}
