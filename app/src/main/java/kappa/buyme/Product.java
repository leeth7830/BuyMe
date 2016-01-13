package kappa.buyme;


import android.widget.ImageView;

public class Product{
	
	private String product_name;
	private String product_image;
	private String product_code;
	private String product_barcode;
	private String product_department;
	private String product_division;
	private String product_family;
	private String product_groupfamily;
	private String product_subfamily;
	private String product_originalprice;
	private String product_lowestprice;
	private String product_qty;
	private String product_value;
	private String product_category;
	private String product_subCategory;
	
	public void setProductName (String product_name)
	{
	    this.product_name = product_name;
	}
	
	public String getProductName()
	{
	    return product_name;
	}
	public void setProductCategory (String product_category)
	{
		this.product_category = product_category;
	}

	public String getProductCategory()
	{
		return product_category;
	}
	public void setProductSubCategory (String product_subCategory)
	{
		this.product_subCategory = product_subCategory;
	}

	public String getProductSubCategory()
	{
		return product_subCategory;
	}

	public void setProductImage (String product_image)
	{
		this.product_image = product_image;
	}

	public String getProductImage()
	{
		return product_image;
	}
	
	public void setProductBarcode (String product_barcode)
	{
	    this.product_barcode = product_barcode;
	}
	
	public String getProductBarcode()
	{
	    return product_barcode;
	}
	
	public void setProductDivision (String product_division)
	{
	    this.product_division = product_division;
	}
	
	public String getProductDivision()
	{
	    return product_division;
	}
	
	public void setProductDepartment (String product_department)
	{
	    this.product_department = product_department;
	}
	
	public String getProductDepartment()
	{
	    return product_department;
	}
	
	public void setProductFamily (String product_family)
	{
	    this.product_family = product_family;
	}
	
	public String getProductFamily()
	{
	    return product_family;
	}
	
	public void setProductGroupFamily (String product_groupfamily)
	{
	    this.product_groupfamily = product_groupfamily;
	}
	
	public String getProductGroupFamily()
	{
	    return product_groupfamily;
	}
	
	public void setProductSubFamily (String product_subfamily)
	{
	    this.product_subfamily = product_subfamily;
	}
	
	public String getProductSubFamily()
	{
	    return product_subfamily;
	}
	

	public void setProductQty (String product_qty)
	{
	    this.product_qty = product_qty;
	}
	
	public String getProductQty()
	{
	    return product_qty;
	}
	
	public void setProductValue (String product_value)
	{
	    this.product_value = product_value;
	}
	
	public String getProductValue()
	{
	    return product_value;
	}
	
	public void setProductCode(String product_code)
	{
	    this.product_code = product_code;
	}
	
	public String getProductCode()
	{
	    return product_code;
	}
}
