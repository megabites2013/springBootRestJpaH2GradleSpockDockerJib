package com.efiab.springdockerjib.utils;

public class Constants {

  public static final double EARTH_RADIUS = 6371; // radius in kilometers

  public static final String THE1PAGE =
      "Java test challenge:\n"
          + "\n"
          + "Write a REST (-y) service that will return the geographic (straight line) distance between two postal codes in the UK.\n"
          + "\n"
          + "Arguments to a request are two UK postal codes (you may decide how these arguments are provided).\n"
          + "\n"
          + "Result to a valid request must be a json document that contains the following information:\n"
          + "\n"
          + "    For both locations, the postal code, latiude and longitude (both in degrees);\n"
          + "\n"
          + "    The distance between the two locations (in kilometers);\n"
          + "\n"
          + "    A fixed string 'unit' that has the value \"km\";\n"
          + "\n"
          + "For postal codes lookup: use the following data.\n"
          + "\n"
          + "http://www.freemaptools.com/download-uk-postcode-lat-lng.htm;\n"
          + "\n"
          + "http://www.freemaptools.com/download/full-postcodes/postcodes.zip;\n"
          + "\n"
          + "http://www.freemaptools.com/download/full-postcodes/fullukpostcodes.zip.\n"
          + "\n"
          + "(You are free to use a database, as long as you give instructions on how to set one up. You may also use the csv file if you find that more convenient).\n"
          + "\n"
          + "TECHNOLOGY REQUIREMENTS:\n"
          + "\n"
          + "Use any technology you like, as long as it runs on the JVM. Obviously, if you want to showcase your knowledge of spring, you'll want to use spring. Be prepared to explain every bit of code/configuration you submit.\n"
          + "\n"
          + "The submitted solution should include a simple way to build (maven preferred) and run on either the command line or from eclipse.\n"
          + "\n"
          + "BONUS FEATURES:\n"
          + "\n"
          + "* Unit tests!\n"
          + "\n"
          + "* Updating postal codes - add REST calls to query and updated the postal-codes -> coordinates \n"
          + "   mapping. Obviously, these need to be persisted;\n"
          + "\n"
          + "* Request logging (log the two post codes in the request; preferably in some way so we can later \n"
          + "   aggregate and report easily);\n"
          + "\n"
          + "* Authentication - restrict the service to those who know a username/password combination;\n"
          + "\n"
          + "(You don't have to do this -- but it will give you more opportunities to show off your knowledge, and give us more to talk about).\n"
          + "\n"
          + "USEFUL CODE:\n"
          + "\n"
          + "This bit of Java code computes (an approximation of) the distance between two points on the planet, given as long/lat pairs (in degrees).\n"
          + "\n"
          + "    Private final static double EARTH_RADIUS = 6371; // radius in kilometers\n"
          + "\n"
          + "    Private double calculateDistance(double latitude, double longitude, double latitude2, double \n"
          + "    longitude2) {\n"
          + "\n"
          + "        // Using Haversine formula! See Wikipedia;\n"
          + "\n"
          + "        double lon1Radians = Math.toRadians(longitude);\n"
          + "\n"
          + "        double lon2Radians = Math.toRadians(longitude2);\n"
          + "\n"
          + "        double lat1Radians = Math.toRadians(latitude);\n"
          + "\n"
          + "        double lat2Radians = Math.toRadians(latitude2);\n"
          + "\n"
          + "        double a = haversine(lat1Radians, lat2Radians)\n"
          + "          + Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);\n"
          + "\n"
          + "        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));\n"
          + "\n"
          + "        return (EARTH_RADIUS * c);\n"
          + "\n"
          + "    }\n"
          + "\n"
          + "    private double haversine(double deg1, double deg2) {\n"
          + "        return square(Math.sin((deg1 - deg2) / 2.0));\n"
          + "\n"
          + "    }\n"
          + "\n"
          + "    private double square(double x) {\n"
          + "        return x * x;\n"
          + "\n"
          + "    }\n"
          + ""
          + ""
          + ""
          + ""
          + "how-to: localhost:8080/\n"
          + "    router.route(\"/gui/*\").handler(StaticHandler.create(\"gui\"));\n"
          + "    router.get(\"/api/postcodes\").handler(this::getAll);\n"
          + "    router.get(\"/api/postcodes/:p\").handler(this::getOne);\n"
          + "    router.get(\"/api/postcodescalc\").handler(this::calcDistence);\n"
          + "    router.post(\"/api/postcodes\").handler(this::addOne);\n"
          + "    router.delete(\"/api/postcodes/:p\").handler(this::deleteOne);\n"
          + "    router.put(\"/api/postcodes/:p\").handler(this::updateOne);";

  public static final String FILE_POSTCODE_OUTCODES_CSV = "postcode-outcodes.csv";
  public static final String COLLECTION = "postcodes";

  public static final String UKPCODE = "UKPOSTCODE";

  public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
  public static final String CONTENT_TYPE = "content-type";
  public static final int MAGIC_TWO = 2;

  public static final String CONNECTION_STRING = "connection_string";
  public static final String DB_NAME = "db_name";
  public static final String COL_POSTCODES_TEST = "postcodes-test";
  public static final String MONGODB_LOCALHOST_27017 = "mongodb://localhost:27017";
}
