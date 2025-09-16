/*
 * This file is part of Horse Racing.
 *
 * Copyright (C) [2025] [IMT Solutions]
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 */
package com.imt.login.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Creates checksum for proxyCGI request parameters.
 */
public class CheckSum {

  private static Map<Integer, Integer> table = new HashMap<Integer, Integer>();
  private static int tableSize;

  static {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    map.put(0, 54319);
    map.put(1, 4129);
    map.put(2, 8258);
    map.put(3, 12387);
    map.put(4, 16516);
    map.put(5, 20645);
    map.put(6, 24774);
    map.put(7, 28903);
    map.put(8, 33032);
    map.put(9, 37161);

    map.put(10, 41290);
    map.put(11, 45419);
    map.put(12, 49548);
    map.put(13, 53677);
    map.put(14, 57806);
    map.put(15, 61935);
    map.put(16, 4657);
    map.put(17, 528);
    map.put(18, 12915);
    map.put(19, 8786);

    map.put(20, 21173);
    map.put(21, 17044);
    map.put(22, 29431);
    map.put(23, 25302);
    map.put(24, 37689);
    map.put(25, 33560);
    map.put(26, 45947);
    map.put(27, 41818);
    map.put(28, 54205);
    map.put(29, 50076);

    map.put(30, 62463);
    map.put(31, 58334);
    map.put(32, 9314);
    map.put(33, 13379);
    map.put(34, 1056);
    map.put(35, 5121);
    map.put(36, 25830);
    map.put(37, 29895);
    map.put(38, 17572);
    map.put(39, 21637);

    map.put(40, 42346);
    map.put(41, 46411);
    map.put(42, 34088);
    map.put(43, 38153);
    map.put(44, 58862);
    map.put(45, 62927);
    map.put(46, 50604);
    map.put(47, 54669);
    map.put(48, 13907);
    map.put(49, 9842);

    map.put(50, 5649);
    map.put(51, 1584);
    map.put(52, 30423);
    map.put(53, 26358);
    map.put(54, 22165);
    map.put(55, 18100);
    map.put(56, 46939);
    map.put(57, 42874);
    map.put(58, 38681);
    map.put(59, 34616);

    map.put(60, 63455);
    map.put(61, 59390);
    map.put(62, 55197);
    map.put(63, 51132);
    map.put(64, 18628);
    map.put(65, 22757);
    map.put(66, 26758);
    map.put(67, 30887);
    map.put(68, 2112);
    map.put(69, 6241);

    map.put(70, 10242);
    map.put(71, 14371);
    map.put(72, 51660);
    map.put(73, 55789);
    map.put(74, 59790);
    map.put(75, 63919);
    map.put(76, 35144);
    map.put(77, 39273);
    map.put(78, 43274);
    map.put(79, 47403);

    map.put(80, 23285);
    map.put(81, 19156);
    map.put(82, 31415);
    map.put(83, 27286);
    map.put(84, 6769);
    map.put(85, 2640);
    map.put(86, 14899);
    map.put(87, 10770);
    map.put(88, 56317);
    map.put(89, 52188);

    map.put(90, 64447);
    map.put(91, 60318);
    map.put(92, 39801);
    map.put(93, 35672);
    map.put(94, 47931);
    map.put(95, 43802);
    map.put(96, 27814);
    map.put(97, 31879);
    map.put(98, 19684);
    map.put(99, 23749);

    map.put(100, 11298);
    map.put(101, 15363);
    map.put(102, 3168);
    map.put(103, 7233);
    map.put(104, 60846);
    map.put(105, 64911);
    map.put(106, 52716);
    map.put(107, 56781);
    map.put(108, 44330);
    map.put(109, 48395);

    map.put(110, 36200);
    map.put(111, 40265);
    map.put(112, 32407);
    map.put(113, 28342);
    map.put(114, 24277);
    map.put(115, 20212);
    map.put(116, 15891);
    map.put(117, 11826);
    map.put(118, 7761);
    map.put(119, 3696);

    map.put(120, 65439);
    map.put(121, 61374);
    map.put(122, 57309);
    map.put(123, 53244);
    map.put(124, 48923);
    map.put(125, 44858);
    map.put(126, 40793);
    map.put(127, 36728);
    map.put(128, 37256);
    map.put(129, 33193);

    map.put(130, 45514);
    map.put(131, 41451);
    map.put(132, 53516);
    map.put(133, 49453);
    map.put(134, 61774);
    map.put(135, 57711);
    map.put(136, 4224);
    map.put(137, 161);
    map.put(138, 12482);
    map.put(139, 8419);

    map.put(140, 20484);
    map.put(141, 16421);
    map.put(142, 28742);
    map.put(143, 24679);
    map.put(144, 33721);
    map.put(145, 37784);
    map.put(146, 41979);
    map.put(147, 46042);
    map.put(148, 49981);
    map.put(149, 54044);

    map.put(150, 58239);
    map.put(151, 62302);
    map.put(152, 689);
    map.put(153, 4752);
    map.put(154, 8947);
    map.put(155, 13010);
    map.put(156, 16949);
    map.put(157, 21012);
    map.put(158, 25207);
    map.put(159, 29270);

    map.put(160, 46570);
    map.put(161, 42443);
    map.put(162, 38312);
    map.put(163, 34185);
    map.put(164, 62830);
    map.put(165, 58703);
    map.put(166, 54572);
    map.put(167, 50445);
    map.put(168, 13538);
    map.put(169, 9411);

    map.put(170, 5280);
    map.put(171, 1153);
    map.put(172, 29798);
    map.put(173, 25671);
    map.put(174, 21540);
    map.put(175, 17413);
    map.put(176, 42971);
    map.put(177, 47098);
    map.put(178, 34713);
    map.put(179, 38840);

    map.put(180, 59231);
    map.put(181, 63358);
    map.put(182, 50973);
    map.put(183, 55100);
    map.put(184, 9939);
    map.put(185, 14066);
    map.put(186, 1681);
    map.put(187, 5808);
    map.put(188, 26199);
    map.put(189, 30326);

    map.put(190, 17941);
    map.put(191, 22068);
    map.put(192, 55628);
    map.put(193, 51565);
    map.put(194, 63758);
    map.put(195, 59695);
    map.put(196, 39368);
    map.put(197, 35305);
    map.put(198, 47498);
    map.put(199, 43435);

    map.put(200, 22596);
    map.put(201, 18533);
    map.put(202, 30726);
    map.put(203, 26663);
    map.put(204, 6336);
    map.put(205, 2273);
    map.put(206, 14466);
    map.put(207, 10403);
    map.put(208, 52093);
    map.put(209, 56156);

    map.put(210, 60223);
    map.put(211, 64286);
    map.put(212, 35833);
    map.put(213, 39896);
    map.put(214, 43963);
    map.put(215, 48026);
    map.put(216, 19061);
    map.put(217, 23124);
    map.put(218, 27191);
    map.put(219, 31254);

    map.put(220, 2801);
    map.put(221, 6864);
    map.put(222, 10931);
    map.put(223, 14994);
    map.put(224, 64814);
    map.put(225, 60687);
    map.put(226, 56684);
    map.put(227, 52557);
    map.put(228, 48554);
    map.put(229, 44427);

    map.put(230, 40424);
    map.put(231, 36297);
    map.put(232, 31782);
    map.put(233, 27655);
    map.put(234, 23652);
    map.put(235, 19525);
    map.put(236, 15522);
    map.put(237, 11395);
    map.put(238, 7392);
    map.put(239, 3265);

    map.put(240, 61215);
    map.put(241, 65342);
    map.put(242, 53085);
    map.put(243, 57212);
    map.put(244, 44955);
    map.put(245, 49082);
    map.put(246, 36825);
    map.put(247, 40952);
    map.put(248, 28183);
    map.put(249, 32310);

    map.put(250, 20053);
    map.put(251, 24180);
    map.put(252, 11923);
    map.put(253, 16050);
    map.put(254, 3793);
    map.put(255, 7920);

    // Conversion table update prohibited
    table = Collections.unmodifiableMap(map);

    tableSize = map.size();
  };

  // Collection of static methods, instance creation prohibited
  private CheckSum() {
  }

  /**
   * Calculates checksum.
   * 
   * @param source Target for checksum calculation
   * @return Calculated 2-character checksum value. Alphabetic parts are uppercase.
   */
  public static String calculate(String source) {
    String[] text = source.split(StringUtils.EMPTY);
    int count = 0;
    int value = 0;

    for (String letter : text) {

      if (tableSize <= count) {
        count -= tableSize;
      }

      byte[] bytes = letter.getBytes(StandardCharsets.US_ASCII);

      value += table.get(count) * bytes[0];
      count++;
    }

    String hex = Integer.toHexString(value);
    return StringUtils.upperCase(StringUtils.right(hex, 2));
  }


  /** About the checksum mechanism
    * Example) The URL for the entry table of the Test Stakes on March 5, 2023 is https://localhost/TESTDB/accessD.html?CNAME=sw01dde 10 06 20230204 1120230305/00 where the last 2 digits 00 are the checksum
    * To obtain the checksum, concatenate the procedure name for each screen (example: sw01dde), cache time (example: 10), parameters (example: 06202302041120230305) and slash (/)
    * and pass the concatenated string to the CheckSum.calculate method.
    * 
    * Procedure names
    * Entry table/detailed entry table page: sw01ddd
    * Odds/win/place odds (by horse number) page: sw151ou
    * Race results/race results page: sw01sde
    *
    * Cache time
    * Entry table/detailed entry table page:
      If current date is before the race date: 01, otherwise: 10
    * Odds/win/place odds (by horse number) page:
      If current date is before the race date: S3, otherwise: 10
    * Race results/race results page:
      If current date is before the race date: 01, otherwise: 10
    *
    * Parameters
    * Entry table/detailed entry table page:
      Venue code + year + round(2 digits) + day(2 digits) + race number(2 digits) + date(8 digits)
      Venue codes:
        Sapporo: 01
        Hakodate: 02
        Fukushima: 03
        Niigata: 04
        Tokyo: 05
        Nakayama: 06
        Chukyo: 07
        Kyoto: 08
        Hanshin: 09
        Kokura: 10
    * Odds/win/place odds (by horse number) page:
      Venue code + year + round(2 digits) + day(2 digits) + race number(2 digits) + date(8 digits) + Z
    * Race results/race results page:
      Venue code + year + round(2 digits) + day(2 digits) + race number(2 digits) + date(8 digits)
    *
    */
;
  public static void main(String[] calculateargs) {
      String test = CheckSum.calculate("sw01ddd1006202302041120230305/");
      //Returns 73, so use this checksum to create URL. Note that entry table/detailed entry table and race results pages can be accessed via both GET and POST, but win/place odds (by horse number) page is only accessible via POST communication
      System.out.println(test);
  }
}
