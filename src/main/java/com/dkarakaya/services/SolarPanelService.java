package com.dkarakaya.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.dkarakaya.databasehelper.DatabaseConnection;
import com.dkarakaya.models.SolarPanel;

public class SolarPanelService {

	// gets all solar panels that exist in db
	public List<SolarPanel> getAllSolarPanels() {
		List<SolarPanel> solarPanels = new ArrayList<SolarPanel>();
		String sql = "select * from solar_panel";

		try {
			Connection conn = DatabaseConnection.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while (rs.next()) {
				SolarPanel sp = new SolarPanel();
				sp.setId(rs.getInt("panel_id"));
				sp.setName(rs.getString("panel_name"));
				sp.setSerial(rs.getString("panel_serial"));
				sp.setTempNOCT(rs.getDouble("panel_tempNOCT"));
				sp.setTempCoef(rs.getDouble("panel_tempCoef"));
				sp.setPowSTC(rs.getDouble("panel_powSTC"));
				solarPanels.add(sp);
			}
			st.close();
			DatabaseConnection.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return solarPanels;
	}

	public double getProducedPowerForAnHour(double tempNOCT, double tempCoef, double powSTC, double tempEnv,
			double cloudiness) {

		double tempCell = 0;
		double powNOCT = 0;
		
		//Calculating the cell temperature at given environment temperature, cloudiness and NOCT temperature
		tempCell = tempEnv + 1.25 * (1 - cloudiness / 100) * (tempNOCT - 20);

		//Calculating the power that will be produced at nominal operating cell temperature 
		powNOCT = powSTC * (1 + tempCoef * (tempCell - 25));

		return powNOCT > 0 ? powNOCT : 0;
	}

}
