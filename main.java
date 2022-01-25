public class main {
    private static final int SIZE = 10_000_000;

    public static void main(String[] args) {
              withOutConcurrency();
              withConcurrency();
    }

    private static void withOutConcurrency() {
        float[] array = new float[SIZE];
        for (int i =0; i < SIZE; i++){
            array[i]=1;
        }
        long before = System.currentTimeMillis();

        for (int i = 0 ; i < SIZE; i++){
            array[i] = (float) (array[i]*Math.sin(0.2f+i/Math.sqrt(0.45f))*
                    Math.cos(0.3f+i/Math.sqrt(0.46f))*Math.sqrt(0.2f+i/Math.sqrt(0.45f))*Math.sin(0.32f + i*50000/Math.sqrt(345.45)));
        }

        long after = System.currentTimeMillis();
        System.out.println(after-before);
    }
    private static void withConcurrency() {
        float[] array = new float[SIZE];
        for (int i =0; i < SIZE; i++){
            array[i]=1;
        }
        long before = System.currentTimeMillis();
                 float [] newArray = new float[SIZE/2];
                 System.arraycopy(array,SIZE/2,newArray,0,SIZE/2);
        Thread thread1 = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < SIZE / 2; i++) {
                            array[i] = (float) (array[i] * Math.sin(0.2f + i / Math.sqrt(0.45f)) *
                                    Math.cos(0.3f + i / Math.sqrt(0.46f)) * Math.sqrt(0.2f + i / Math.sqrt(0.45f))
                                    * Math.sin(0.32f + i * 50000 / Math.sqrt(345.45)));
                        }
                    }
                });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < SIZE / 2; i++) {
                    newArray[i] = (float) (newArray[i] * Math.sin(0.2f + i / Math.sqrt(0.45f)) *
                            Math.cos(0.3f + i / Math.sqrt(0.46f)) * Math.sqrt(0.2f + i / Math.sqrt(0.45f))
                            * Math.sin(0.32f + i * 50000 / Math.sqrt(345.45)));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(newArray,0,array,SIZE/2,SIZE/2);
        long after = System.currentTimeMillis();
        System.out.println(after-before);
        }


    }


