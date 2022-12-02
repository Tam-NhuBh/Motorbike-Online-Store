package com.MotorbikeStore.dao.impl;

import java.util.List;

import com.MotorbikeStore.dao.IProductDetailDAO;
import com.MotorbikeStore.mapper.ProductDetailMapper;
import com.MotorbikeStore.model.ProductDetailModel;

public class ProductDetailDAO extends AbstractDAO<ProductDetailModel> implements IProductDetailDAO {

	@Override
	public List<ProductDetailModel> findAll() {
		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id and s.motors_id = m.motors_id;";

		return query(sql, new ProductDetailMapper());
	}

	@Override
	public List<ProductDetailModel> findByMotorcyclesID(int moID) {

		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id and m.motors_id = ? and s.motors_id = m.motors_id;";

		return query(sql, new ProductDetailMapper(), moID);
	}

	@Override
	public List<ProductDetailModel> findNumberOfMotor(int numberMotor) {
		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold , motor_desc from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id and s.motors_id = m.motors_id LIMIT ? ;";
				

		return query(sql, new ProductDetailMapper(), numberMotor);
	}

	@Override
	public ProductDetailModel findOneByAmotorId(int aMotorId) {
		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id and a.a_motor_id = ? and s.motors_id = m.motors_id;";
		
		List<ProductDetailModel> list = query(sql, new ProductDetailMapper(), aMotorId);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<ProductDetailModel> searchByName(String txtSearch) {
		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id && aMotorDesc like ? and s.motors_id = m.motors_id ;";
		
		return query(sql, new ProductDetailMapper(), "%"+txtSearch+"%");
	}

	@Override
	public List<ProductDetailModel> filterPriceAround(int fromNum, int toNum) {
		String sql = "select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id &&  price between ? and ? and s.motors_id = m.motors_id ;";
		return query(sql, new ProductDetailMapper(), fromNum, toNum);
	}

	@Override
	public List<ProductDetailModel> filterColor(String color) {
		String sql ="select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id && color like ? and s.motors_id = m.motors_id;";
		return query(sql, new ProductDetailMapper(), "%"+color+"%");
	}

	@Override
	public List<ProductDetailModel> filterCC(int fromNum, int toNum) {
		String sql = "select aMotorDesc, s.engine_torque, a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s, motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id && s.motors_id = m.motors_id && s.engine_torque between ? and ?;";
		return query(sql, new ProductDetailMapper(), fromNum, toNum);
	}

	@Override
	public List<ProductDetailModel> filterBranch(String branch) {
		String sql ="select aMotorDesc, s.engine_torque,a.motors_id, a_motor_id, motors_name, version, motor_type, branch, style,color, price,date_insert, check_sold,  motor_desc  from specification as s,motorcycles as m, a_motorcycle as a where a.motors_id = m.motors_id && branch like ? and s.motors_id = m.motors_id;";
		return query(sql, new ProductDetailMapper(), "%"+branch+"%");
	}

}
