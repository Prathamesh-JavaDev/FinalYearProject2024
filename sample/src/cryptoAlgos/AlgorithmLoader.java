package cryptoAlgos;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class AlgorithmLoader {
    public static List<Class<? extends CryptoAlgorithm>> loadAlgorithmClasses() {
        List<Class<? extends CryptoAlgorithm>> classes = new ArrayList<>();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources("cryptoAlgos");

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());

                if (directory.exists()) {
                    File[] files = directory.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile() && file.getName().endsWith(".class")) {
                                String className = file.getName().replace(".class", "");
                                Class<?> loadedClass = Class.forName("cryptoAlgos." + className);
                                if (CryptoAlgorithm.class.isAssignableFrom(loadedClass) && !loadedClass.equals(CryptoAlgorithm.class)) {
                                    classes.add((Class<? extends CryptoAlgorithm>) loadedClass);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }
}
