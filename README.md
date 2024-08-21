# staticpropertyanalyser
Gradle plugin with tasks and features to analyse spring static property files and configurations

## Planned features

### Analyse redundancy in static property files for a set of profiles

Given a list of profiles, the plugin should be able to detect properties that are being repeated across multiple profiles. This is useful to identify properties that are common across profiles and can be moved to a common file.

The profile list goes from least specific profile to most specific profile. 
For example, the list could be `eu,eu-staging`.
Properties in eu-staging would be considered more specific than properties in eu.

The task could be named "analyseRedundancy" and could be run as follows:

```shell
./gradlew analyseRedundancy -Pprofiles=profile1,profile2,profile3
```

The result should be a list of the parsed files and the redundant properties.


### Order properties in alphabetical order

Given a properties file, order the properties in alphabetical order.

Task should be configurable via DSL.

Eg:
```groovy
tasks.register("orderPropertiesAlphabetically", io.github.zaragozamartin91.staticpropertyanalyser.task.OrderPropertiesAlphabeticallyTask) {
    group = 'StaticProperties'
    profiles = ['us', 'us-staging', 'us-production']
    gapLinesBetweenGroups = 1
}
```
