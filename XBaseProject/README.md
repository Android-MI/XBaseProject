项目根目录下 build.gradle
buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath 'com.jakewharton:butterknife-gradle-plugin:8.8.1'
        classpath 'me.tatarka:gradle-retrolambda:3.7.0'
    }
}


app目录下 build.gradle

apply plugin: 'me.tatarka.retrolambda'
repositories {
    maven { url "http://dl.bintray.com/fotoapparat/fotoapparat" }
}


android {

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
 }
 //使用下列代码,会将代码编译到兼容1.6的字节码格式,android2.3.3-4.4使用的jdk6
 retrolambda {
     javaVersion JavaVersion.VERSION_1_6
 }



## 发布
> Use the publish closure to set the info of your package:

```publish {
    userOrg = 'novoda'
    groupId = 'com.novoda'
    artifactId = 'bintray-release'
    publishVersion = '0.6.1'
    desc = 'Oh hi, this is a nice description for a project, right?'
    website = 'https://github.com/novoda/bintray-release'
}```

> Finally, use the task bintrayUpload to publish (make sure you build the project first!):

``` $ ./gradlew clean build bintrayUpload -PbintrayUser=BINTRAY_USERNAME -PbintrayKey=BINTRAY_KEY -PdryRun=false ```
