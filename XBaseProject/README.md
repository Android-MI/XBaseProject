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
