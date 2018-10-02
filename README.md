# springBootRestJpaH2GradleSpockDockerJib
A demo springBoot Rest application with Jpa H2 Gradle Spock Geb Docker Jib and groovy, etc

Requirement is as blow:


Write a REST service that will return the geographic (straight line) distance between two postal codes in the UK.



Result to a valid request must be a json document that contains the following information:

    For both locations, the postal code, latiude and longitude (both in degrees);

    The distance between the two locations (in kilometers);

    A fixed string 'unit' that has the value "km";
    

For postal codes lookup: use the following data.

http://www.freemaptools.com/download-uk-postcode-lat-lng.htm;

http://www.freemaptools.com/download/full-postcodes/postcodes.zip;

http://www.freemaptools.com/download/full-postcodes/fullukpostcodes.zip.

