package com.efiab.springdockerjib.resource;

import com.efiab.springdockerjib.model.PostCode;
import com.efiab.springdockerjib.model.PostCodeDistance;
import com.efiab.springdockerjib.repository.IPostCodeRepository;
import com.efiab.springdockerjib.utils.DistanceCalc;
import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.efiab.springdockerjib.utils.Constants.*;

@Api
@RestController
@RequestMapping(value = "/api/postcodes")
public class PostCodeResource {

  /** The Logger. */
  private static final Logger LOGGER = LogManager.getLogger(UKPCODE);

  @Autowired IPostCodeRepository postCodeRepository;

  @ApiOperation(value = "List All PostCodes", notes = "List all postcodes in DB")
  @GetMapping(value = "/all")
  public List<PostCode> getAll() {
    List<PostCode> all = postCodeRepository.findAll();
    if (all.isEmpty()) {
      initDB();
      return postCodeRepository.findAll();
    }
    return all;
  }

  @ApiOperation(value = "Create Multiple PostCode", notes = "Save posted PostCodes by http Post")
  @PostMapping(value = "/saveall")
  public List<PostCode> persist(@RequestBody final List<PostCode> postCodes) {
    postCodeRepository.saveAll(postCodes);
    return postCodeRepository.findAll();
  }

  @ApiOperation(value = "Create One PostCode", notes = "save one PostCode by http post")
  @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PostCode persist(@RequestBody final PostCode postCode) {
    return postCodeRepository.save(postCode);
  }

  @ApiOperation(value = "Update One PostCode", notes = "update one PostCode")
  @PutMapping(value = "/{postCodeId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public PostCode update(@PathVariable Integer postCodeId, @RequestBody final PostCode postCode) {
    LOGGER.debug("PostCodeResource - updateOne: id = " + postCodeId);
    postCode.setId(postCodeId);
    return postCodeRepository.save(postCode);
  }

  @ApiOperation(value = "Find One PostCode by id", notes = "find one PostCode by id")
  @GetMapping("/{postCodeId}")
  public Optional<PostCode> find(@PathVariable final Integer postCodeId) {
    return postCodeRepository.findById(postCodeId);
  }

  @ApiOperation(value = "Delete One PostCode", notes = "delete one PostCode")
  @DeleteMapping("/{postCodeId}")
  public List<PostCode> delete(@PathVariable final Integer postCodeId) {
    postCodeRepository.deleteById(postCodeId);
    return postCodeRepository.findAll();
  }

  /** below is to Calculate the straight line distance between 2 UK postcodes */
  @ApiOperation(
      value = "Calculate the straight line distance between 2 UK postcodes",
      notes = "Calculate the straight line distance between 2 UK postcodes")
  @GetMapping(value = "/calc/{postCodeId1}/{postCodeId2}")
  public PostCodeDistance calc(
      @PathVariable final Integer postCodeId1, @PathVariable final Integer postCodeId2) {
    List<PostCode> postCodes =
        postCodeRepository.findAllById(Arrays.asList(postCodeId1, postCodeId2));
    LOGGER.debug("PostCodeResource - calcDistence: count = " + postCodes.size());
    if (postCodes.size() != MAGIC_TWO) {
      return null;
    } else {
      PostCode p1 = postCodes.get(0);
      LOGGER.debug("PostCodeResource - calcDistence: p1 = " + p1.toString());

      PostCode p2 = postCodes.get(1);
      LOGGER.debug("PostCodeResource - calcDistence: p2 = " + p2.toString());
      PostCodeDistance pcd = DistanceCalc.calculateDistance(p1, p2);
      LOGGER.info("calcDistence = " + pcd.toJsonObj());
      return pcd;
    }
  }

  /** below is to chean and load db with demo values, becareful to use!!! */
  @Async
  @ApiOperation(
      value = "Clean and load db with demo values",
      notes = "purge and reload data, becareful to use!!!")
  @GetMapping(value = "/reload")
  public CompletableFuture<List<PostCode>> initDB() {
    postCodeRepository.deleteAll();
    postCodeRepository.saveAll(loadPostCodeData());
    return CompletableFuture.completedFuture(postCodeRepository.findAll());
  }

  /**
   * Load some demo UK postcode from reading a boundle csv file.
   *
   * @return the List<PostCode> later import to db
   */
  private List<PostCode> loadPostCodeData() {
    InputStream in =
        PostCodeResource.class.getClassLoader().getResourceAsStream(FILE_POSTCODE_OUTCODES_CSV);
    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    List<PostCode> postCodeList = new ArrayList<>();
    try {
      postCodeList = new CsvToBeanBuilder(reader).withType(PostCode.class).build().parse();
      LOGGER.debug("processing number of  entries in CSV file:" + postCodeList.size());
    } catch (Exception e) {
      LOGGER.error("processing csv file error:" + e.getMessage());
    }
    return postCodeList;
  }
}
