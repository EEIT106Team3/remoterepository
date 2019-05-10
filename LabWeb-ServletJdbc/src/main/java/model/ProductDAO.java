package model;

import java.util.List;

public interface ProductDAO {

	public abstract ProductBean findByPrimaryKey(int id);

	public abstract List<ProductBean> findAll();

	public abstract ProductBean insert(ProductBean bean);

	public abstract ProductBean update(String name, double price,
			java.util.Date make, int expire, int id);

	public abstract boolean delete(int id);

}