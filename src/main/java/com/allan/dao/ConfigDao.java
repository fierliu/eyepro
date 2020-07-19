package com.allan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.allan.domain.Config;
import com.allan.utils.Cache;
import com.allan.utils.SqliteHelper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

public class ConfigDao {

	JdbcTemplate jdbcTemplate = SqliteHelper.getJdbcTemplate();

	public void load(){
		Cache.getCache().setConfigCache(this.findAll());
	}

	public Config findAll() {
		List<Config> config = jdbcTemplate.query("select * from config", new RowMapper<Config>() {
			@Override
			public Config mapRow(ResultSet resultSet, int i) throws SQLException {
				Config config = new Config();
				config.setBreakTime(resultSet.getString("break_time"));
				config.setPopupOn(resultSet.getString("popup_on"));
				config.setPopupSize(resultSet.getString("popup_size"));
				config.setNoticeText(resultSet.getString("notice_text"));
				config.setMusicOn(resultSet.getString("music_on"));
				config.setSpvttsOn(resultSet.getString("spvtts_on"));
				config.setSpvttsChOn(resultSet.getString("spvtts_ch_on"));
				config.setFreettsOn(resultSet.getString("freetts_on"));
				config.setMute(resultSet.getString("mute"));
				config.setDayCountdown(resultSet.getString("day_countdown"));
				config.setMission(resultSet.getString("mission"));
				return config;
			}
		});
		return config.get(0);
	}

	public void updateConfig(Config config) {
		StringBuffer sql = new StringBuffer();
		 sql.append("UPDATE config SET ");
		if (!StringUtils.isEmpty(config.getBreakTime())) {
			sql.append(" break_time = '");
			sql.append(config.getBreakTime());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getPopupOn())) {
			sql.append(" popup_on = '");
			sql.append(config.getPopupOn());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getPopupSize())) {
			sql.append(" popup_size = '");
			sql.append(config.getPopupSize());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getNoticeText())) {
			sql.append(" notice_text = '");
			sql.append(config.getNoticeText());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getMusicOn())) {
			sql.append(" music_on = '");
			sql.append(config.getMusicOn());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getSpvttsOn())) {
			sql.append(" spvtts_on = '");
			sql.append(config.getSpvttsOn());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getSpvttsChOn())) {
			sql.append(" spvtts_ch_on = '");
			sql.append(config.getSpvttsChOn());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getFreettsOn())) {
			sql.append(" freetts_on = '");
			sql.append(config.getFreettsOn());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getMute())) {
			sql.append(" mute = '");
			sql.append(config.getMute());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getDayCountdown())) {
			sql.append(" day_countdown = '");
			sql.append(config.getDayCountdown());
			sql.append("', ");
		}
		if (!StringUtils.isEmpty(config.getMission())) {
			sql.append(" mission = '");
			sql.append(config.getMission());
			sql.append("', ");
		}
		sql.deleteCharAt(sql.length()-2);//删掉最后的,
		sql.append("WHERE id = '1'");
		System.out.println("sql = " + sql);
		jdbcTemplate.execute(sql.toString());
	}
}
