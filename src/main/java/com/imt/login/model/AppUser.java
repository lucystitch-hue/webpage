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
package com.imt.login.model;


import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import com.imt.login.dto.FcmTokenUserDTO;


@Entity
@Table(name = "A01", schema = "TEST3")
@SqlResultSetMapping(name = "getUsersToPushNotification", classes = {
        @ConstructorResult(targetClass = FcmTokenUserDTO.class, columns = {
                @ColumnResult(name = "userId", type = String.class),
                @ColumnResult(name = "pushThisWeek", type = String.class),
                @ColumnResult(name = "pushNews", type = String.class),
                @ColumnResult(name = "userKbn", type = String.class),}) })
@NamedNativeQuery(name = "AppUser.getUsersToPushNotification",
        query ="SELECT A01.USERID as userId, A01.PUSHTHISWEEK as pushThisWeek, A01.PUSHNEWS as pushNews, A01.USERKBN as userKbn "
        		+ "FROM TEST3.A01 A01 "
        		+ "WHERE A01.MSYFLG = '1' AND "
        		+ "(TRIM(:category) IS NULL " +
                "OR :category NOT IN ('90', '91', '10', '11', '12', '13', '19', '20', '50', '51', '52', '53', '54', '61', '62', '63', '64') " +
                "OR (:category = '10' AND A01.PUSHTORIKESHI = '1') " +
                "OR (:category = '11' AND A01.PUSHJOGAI = '1') " +
                "OR (:category = '12' AND A01.PUSHKISYU = '1') " +
                "OR (:category = '13' AND A01.PUSHJIKOKU = '1') " +
                "OR (:category = '19' AND A01.PUSHBABA = '1') " +
                "OR (:category = '20' AND A01.PUSHWEATHER = '1') " +
                "OR (:category = '50' AND A01.PUSHUTOKUBETSU = '1') " +
                "OR (:category = '51' AND A01.PUSHMOKUDEN = '1') " +
                "OR (:category = '52' AND A01.PUSHYOKUDEN = '1') " +
                "OR (:category = '53' AND A01.PUSHUMAWEIGHT = '1') " +
                "OR (:category = '54' AND A01.PUSHHARAI = '1') " +
    			"OR (:category = '61' AND A01.PUSHFAVTOKUBETSU = '1') " +
    			"OR (:category = '62' AND A01.PUSHFAVYOKUDEN = '1') " +
    			"OR (:category = '63' AND A01.PUSHFAVHARAI = '1') " +
    			"OR (:category = '64' AND A01.PUSHFAVJOC = '1') " +
                "OR (:category = '90' AND A01.PUSHTHISWEEK = '1') " +
                "OR (:category = '91' AND A01.PUSHNEWS = '1')) " +
                "AND (TRIM(:target) IS NULL OR :target NOT IN ('1') OR (:target = '1' AND A01.USERKBN = '9'))",
        resultSetMapping = "getUsersToPushNotification")

public class AppUser {
    @Id
    @Column(name = "USERID", nullable = false, columnDefinition = "CHAR(40)")
    private String userId;

    @Column(name = "UPASS", nullable = false, length = 32)
    private String password;

    @Column(name = "SEI", length = 20)
    private String sei; // Family name (sei = surname in Japanese)

    @Column(name = "MEI", length = 20)
    private String mei; // Given name (mei = first name in Japanese)

    @Column(name = "SEIKANA", length = 20)
    private String seiKana; // Family name in Katakana (kana = Japanese phonetic script)

    @Column(name = "MEIKANA", length = 20)
    private String meiKana; // Given name in Katakana (kana = Japanese phonetic script)

    @Column(name = "BIRTHDAY", nullable = false, columnDefinition = "CHAR(8)")
    private String birthday;

    @Column(name = "EMAIL", length = 256, nullable = false)
    private String email;

    @Column(name = "POSTCODE", columnDefinition = "CHAR(7)")
    private String postcode;

    @Column(name = "SEX", columnDefinition = "CHAR(1)")
    private String sex;

    @Column(name = "JOB", columnDefinition = "CHAR(2)")
    private String job;

    @Column(name = "STARTYEAR", columnDefinition = "CHAR(4)")
    private String startYear;

    @Column(name = "USERVICE", columnDefinition = "CHAR(15)")
    private String userService;

    @Column(name = "UREASON", columnDefinition = "CHAR(15)")
    private String userReason;

    @Column(name = "UDAYOFF", columnDefinition = "CHAR(15)")
    private String userDayOff;

    @Column(name = "IPATID1", columnDefinition = "CHAR(8)")
    private String ipatId1;

    @Column(name = "IPATPARS1", columnDefinition = "CHAR(4)")
    private String ipatPars1;

    @Column(name = "IPATPASS1", columnDefinition = "CHAR(4)")
    private String ipatPass1;

    @Column(name = "YSNFLG1", columnDefinition = "CHAR(1)")
    private String ysnFlag1;

    @Column(name = "IPATID2", columnDefinition = "CHAR(8)")
    private String ipatId2;

    @Column(name = "IPATPARS2", columnDefinition = "CHAR(4)")
    private String ipatPars2;

    @Column(name = "IPATPASS2", columnDefinition = "CHAR(4)")
    private String ipatPass2;

    @Column(name = "YSNFLG2", columnDefinition = "CHAR(1)")
    private String ysnFlag2;

    @Column(name = "RENFLG", columnDefinition = "CHAR(1)")
    private String renFlag;

    @Column(name = "RENSNS", columnDefinition = "CHAR(1)")
    private String renSns;

    @Column(name = "YEAR20", columnDefinition = "CHAR(8)")
    private String year20;

    @Column(name = "PUSHTORIKESHI", columnDefinition = "CHAR(1)")
    private String pushTorikeshi = "0"; // Push notification for race cancellation (torikeshi = cancellation)

    @Column(name = "PUSHJOGAI", columnDefinition = "CHAR(1)")
    private String pushJogai = "0"; // Push notification for horse exclusion (jogai = exclusion)

    @Column(name = "PUSHKISYU", columnDefinition = "CHAR(1)")
    private String pushKisyu = "0"; // Push notification for jockey change (kisyu = jockey)

    @Column(name = "PUSHJIKOKU", columnDefinition = "CHAR(1)")
    private String pushJikoku = "0"; // Push notification for time change (jikoku = time)

    @Column(name = "PUSHBABA", columnDefinition = "CHAR(1)")
    private String pushBaba = "0"; // Push notification for track condition (baba = racecourse/track)

    @Column(name = "PUSHWEATHER", columnDefinition = "CHAR(1)")
    private String pushWeather = "0";

    @Column(name = "PUSHUMAWEIGHT", columnDefinition = "CHAR(1)")
    private String pushUmaWeight = "0";

    @Column(name = "PUSHHARAI", columnDefinition = "CHAR(1)")
    private String pushHarai = "0"; // Push notification for payout (harai = payment/payout)

    @Column(name = "PUSHUTOKUBETSU", columnDefinition = "CHAR(1)")
    private String pushUtokubetsu = "0"; // Push notification for special races (tokubetsu = special)

    @Column(name = "PUSHMOKUDEN", columnDefinition = "CHAR(1)")
    private String pushMokuden = "0"; // Push notification for race participants (mokuden = participants confirmation)

    @Column(name = "PUSHYOKUDEN", columnDefinition = "CHAR(1)")
    private String pushYokuden = "0"; // Push notification for race forecast (yokuden = forecast/advance notice)

    @Column(name = "PUSHTHISWEEK", columnDefinition = "CHAR(1)")
    private String pushThisWeek = "0";

    @Column(name = "PUSHNEWS", columnDefinition = "CHAR(1)")
    private String pushNews = "0";

    @Column(name = "PUSHYOBI1", columnDefinition = "CHAR(1)")
    private String pushYobi1 = "0"; // Push notification reserve field 1 (yobi = reserve/spare)

    @Column(name = "PUSHYOBI2", columnDefinition = "CHAR(1)")
    private String pushYobi2 = "0"; // Push notification reserve field 2 (yobi = reserve/spare)

    @Column(name = "USERKBN", columnDefinition = "CHAR(1)")
    private String userKbn = "0"; // User classification (kbn = kubun = category/classification)

    @Column(name = "MSYFLG", columnDefinition = "CHAR(1)")
    private String msyFlag = "0";

    @Column(name = "WITHDRAWREASON1", columnDefinition = "CHAR(15)")
    private String withdrawReason1;

    @Column(name = "WITHDRAWREASON2", columnDefinition = "CHAR(15)")
    private String withdrawReason2;

    @Column(name = "WITHDRAWREASONTEXT", length = 500)
    private String withdrawReasonText;

    @Column(name = "LATESTLOGIN", columnDefinition = "CHAR(12)")
    private String latestLogin;

    @Column(name = "INITIALYMD", columnDefinition = "CHAR(18)")
    private String initialYmd;

    @Column(name = "UMACAFLG", columnDefinition = "CHAR(1)")
    private String umacaFlg; // UMACA (electronic money system) flag
    
    @Column(name = "PUSHFAVTOKUBETSU", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String pushFavTokubetsu;
    
    @Column(name = "PUSHFAVYOKUDEN", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String pushFavYokuden;
    
    @Column(name = "PUSHFAVHARAI", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String pushFavHarai;
    
    @Column(name = "PUSHFAVJOC", columnDefinition = "CHAR(1) DEFAULT '1'")
    private String pushFavJoc;

    @Column(name = "UPDTIMESTAMP")
    private LocalDateTime updateTimestamp;

    @Column(name = "PENDING_EMAIL", length = 256)
    private String pendingEmail;

    public String getPushFavTokubetsu() {
		return pushFavTokubetsu;
	}

	public void setPushFavTokubetsu(String pushFavTokubetsu) {
		this.pushFavTokubetsu = pushFavTokubetsu;
	}

	public String getPushFavYokuden() {
		return pushFavYokuden;
	}

	public void setPushFavYokuden(String pushFavYokuden) {
		this.pushFavYokuden = pushFavYokuden;
	}

	public String getPushFavHarai() {
		return pushFavHarai;
	}

	public void setPushFavHarai(String pushFavHarai) {
		this.pushFavHarai = pushFavHarai;
	}

	public String getPushFavJoc() {
		return pushFavJoc;
	}

	public void setPushFavJoc(String pushFavJoc) {
		this.pushFavJoc = pushFavJoc;
	}

	public String getPendingEmail() {
        return pendingEmail;
    }

    public void setPendingEmail(String pendingEmail) {
        this.pendingEmail = pendingEmail;
    }

    public String getUmacaFlg() {
        return umacaFlg;
    }

    public void setUmacaFlg(String umacaFlg) {
        this.umacaFlg = umacaFlg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSei() {
        return sei;
    }

    public void setSei(String sei) {
        this.sei = sei;
    }

    public String getMei() {
        return mei;
    }

    public void setMei(String mei) {
        this.mei = mei;
    }

    public String getSeiKana() {
        return seiKana;
    }

    public void setSeiKana(String seiKana) {
        this.seiKana = seiKana;
    }

    public String getMeiKana() {
        return meiKana;
    }

    public void setMeiKana(String meiKana) {
        this.meiKana = meiKana;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getUserService() {
        return userService;
    }

    public void setUserService(String userService) {
        this.userService = userService;
    }

    public String getUserReason() {
        return userReason;
    }

    public void setUserReason(String userReason) {
        this.userReason = userReason;
    }

    public String getUserDayOff() {
        return userDayOff;
    }

    public void setUserDayOff(String userDayOff) {
        this.userDayOff = userDayOff;
    }

    public String getIpatId1() {
        return ipatId1;
    }

    public void setIpatId1(String ipatId1) {
        this.ipatId1 = ipatId1;
    }

    public String getIpatPars1() {
        return ipatPars1;
    }

    public void setIpatPars1(String ipatPars1) {
        this.ipatPars1 = ipatPars1;
    }

    public String getIpatPass1() {
        return ipatPass1;
    }

    public void setIpatPass1(String ipatPass1) {
        this.ipatPass1 = ipatPass1;
    }

    public String getYsnFlag1() {
        return ysnFlag1;
    }

    public void setYsnFlag1(String ysnFlag1) {
        this.ysnFlag1 = ysnFlag1;
    }

    public String getIpatId2() {
        return ipatId2;
    }

    public void setIpatId2(String ipatId2) {
        this.ipatId2 = ipatId2;
    }

    public String getIpatPars2() {
        return ipatPars2;
    }

    public void setIpatPars2(String ipatPars2) {
        this.ipatPars2 = ipatPars2;
    }

    public String getIpatPass2() {
        return ipatPass2;
    }

    public void setIpatPass2(String ipatPass2) {
        this.ipatPass2 = ipatPass2;
    }

    public String getYsnFlag2() {
        return ysnFlag2;
    }

    public void setYsnFlag2(String ysnFlag2) {
        this.ysnFlag2 = ysnFlag2;
    }

    public String getRenFlag() {
        return renFlag;
    }

    public void setRenFlag(String renFlag) {
        this.renFlag = renFlag;
    }

    public String getRenSns() {
        return renSns;
    }

    public void setRenSns(String renSns) {
        this.renSns = renSns;
    }

    public String getYear20() {
        return year20;
    }

    public void setYear20(String year20) {
        this.year20 = year20;
    }

    public String getPushTorikeshi() {
        return pushTorikeshi;
    }

    public void setPushTorikeshi(String pushTorikeshi) {
        this.pushTorikeshi = pushTorikeshi;
    }

    public String getPushJogai() {
        return pushJogai;
    }

    public void setPushJogai(String pushJogai) {
        this.pushJogai = pushJogai;
    }

    public String getPushKisyu() {
        return pushKisyu;
    }

    public void setPushKisyu(String pushKisyu) {
        this.pushKisyu = pushKisyu;
    }

    public String getPushJikoku() {
        return pushJikoku;
    }

    public void setPushJikoku(String pushJikoku) {
        this.pushJikoku = pushJikoku;
    }

    public String getPushBaba() {
        return pushBaba;
    }

    public void setPushBaba(String pushBaba) {
        this.pushBaba = pushBaba;
    }

    public String getPushWeather() {
        return pushWeather;
    }

    public void setPushWeather(String pushWeather) {
        this.pushWeather = pushWeather;
    }

    public String getPushUmaWeight() {
        return pushUmaWeight;
    }

    public void setPushUmaWeight(String pushUmaWeight) {
        this.pushUmaWeight = pushUmaWeight;
    }

    public String getPushHarai() {
        return pushHarai;
    }

    public void setPushHarai(String pushHarai) {
        this.pushHarai = pushHarai;
    }

    public String getPushUtokubetsu() {
        return pushUtokubetsu;
    }

    public void setPushUtokubetsu(String pushUtokubetsu) {
        this.pushUtokubetsu = pushUtokubetsu;
    }

    public String getPushMokuden() {
        return pushMokuden;
    }

    public void setPushMokuden(String pushMokuden) {
        this.pushMokuden = pushMokuden;
    }

    public String getPushYokuden() {
        return pushYokuden;
    }

    public void setPushYokuden(String pushYokuden) {
        this.pushYokuden = pushYokuden;
    }

    public String getPushThisWeek() {
        return pushThisWeek;
    }

    public void setPushThisWeek(String pushThisWeek) {
        this.pushThisWeek = pushThisWeek;
    }

    public String getPushNews() {
        return pushNews;
    }

    public void setPushNews(String pushNews) {
        this.pushNews = pushNews;
    }

    public String getPushYobi1() {
        return pushYobi1;
    }

    public void setPushYobi1(String pushYobi1) {
        this.pushYobi1 = pushYobi1;
    }

    public String getPushYobi2() {
        return pushYobi2;
    }

    public void setPushYobi2(String pushYobi2) {
        this.pushYobi2 = pushYobi2;
    }

    public String getUserKbn() {
        return userKbn;
    }

    public void setUserKbn(String userKbn) {
        this.userKbn = userKbn;
    }

    public String getMsyFlag() {
        return msyFlag;
    }

    public void setMsyFlag(String msyFlag) {
        this.msyFlag = msyFlag;
    }

    public String getWithdrawReason1() {
        return withdrawReason1;
    }

    public void setWithdrawReason1(String withdrawReason1) {
        this.withdrawReason1 = withdrawReason1;
    }

    public String getWithdrawReason2() {
        return withdrawReason2;
    }

    public void setWithdrawReason2(String withdrawReason2) {
        this.withdrawReason2 = withdrawReason2;
    }

    public String getWithdrawReasonText() {
        return withdrawReasonText;
    }

    public void setWithdrawReasonText(String withdrawReasonText) {
        this.withdrawReasonText = withdrawReasonText;
    }

    public String getLatestLogin() {
        return latestLogin;
    }

    public void setLatestLogin(String latestLogin) {
        this.latestLogin = latestLogin;
    }

    public String getInitialYmd() {
        return initialYmd;
    }

    public void setInitialYmd(String initialYmd) {
        this.initialYmd = initialYmd;
    }

    public LocalDateTime getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(LocalDateTime updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

}