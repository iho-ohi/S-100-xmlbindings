/*
 * Copyright (c) 2024 GLA Research and Development Directorate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grad.eNav.s100.utils;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * The Specific Jar Class-Loader Utility
 * <p/>
 * This utility allows us to load the resources from only specific JAR. This
 * might be useful fow loading the S-100 GML files from given libraries included
 * in the source-code dependencies.
 *
 * @author Nikolaos Vastardis (email: Nikolaos.Vastardis@gla-rad.org)
 */
public class SpecificJarClassLoader extends URLClassLoader {

    /**
     * Instantiates a new specific jar class-loader for a jar URL.
     *
     * @param jarFileUrl the jar file url
     */
    public SpecificJarClassLoader(URL jarFileUrl) {
        super(new URL[]{jarFileUrl});
    }

    /**
     * Instantiates a new specific jar class-loader for a clas in included in
     * the jar.
     *
     * @param clazz the clazz
     */
    public SpecificJarClassLoader(Class<?> clazz) {
        super(new URL[]{byGetResource(clazz)});
    }

    /**
     * Overriding the fineResource URLClassLoader function.
     *
     * @param name the resource name
     * @return the resource URL
     */
    @Override
    public URL findResource(String name) {
        // Only search for resources within the specific JAR file
        return super.findResource(name);
    }

    /**
     * Overriding the getResourceAsStream URLClassLoader function.
     *
     * @param name the resource name
     * @return the resource input stream
     */
    @Override
    public InputStream getResourceAsStream(String name) {
        // Only load resources from the specific JAR file
        return super.getResourceAsStream(name);
    }

    /**
     * This static function allows us to pick up the jar which includes the
     * definition of the provided class. This will then enable us to only load
     * the resources of that specific jar.
     *
     * @param clazz the class that points to a specific jar
     * @return the URL of the jar that includes the specified class
     */
    private static URL byGetResource(Class<?> clazz) {
        URL classResource = clazz.getResource(clazz.getSimpleName() + ".class");
        if (classResource == null) {
            throw new RuntimeException("class resource is null");
        }
        String url = classResource.toString();
        if (url.startsWith("jar:file:")) {
            // extract 'file:......jarName.jar' part from the url string
            String path = url.replaceAll("^jar:(file:.*[.]jar)!/.*", "$1");
            try {
                return new URI(path).toURL();
            } catch (Exception e) {
                throw new RuntimeException("Invalid Jar File URL String");
            }
        }
        throw new RuntimeException("Invalid Jar File URL String");
    }
}
