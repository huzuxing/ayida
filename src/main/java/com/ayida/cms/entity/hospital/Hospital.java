package com.ayida.cms.entity.hospital;

import com.google.gson.annotations.Expose;

public class Hospital implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Expose
	private Integer id;
	
	@Expose
	private String name;
	
	@Expose
	private Double longitude;
	
	@Expose
	private Double latitude;
	
	@Expose
	private String address;
	
	@Expose
	private String desc;
	
	/**所在城市名称**/
	@Expose
	private String cityName;
	
	/**医院属性**/
	@Expose
	private String belongings;
	
	public String getBelongings()
	{
		return belongings;
	}

	public void setBelongings(String belongings)
	{
		this.belongings = belongings;
	}

	public String getCityName()
	{
		return cityName;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the longitude
	 */
	public Double getLongitude()
	{
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public Double getLatitude()
	{
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	

	/**
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
}
