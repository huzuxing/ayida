package com.ayida.cms.entity.doctor;

import com.google.gson.annotations.Expose;

public class DoctorCount extends BaseDoctor
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Expose
	private Integer dayCount;

	@Expose
	private Integer weekCount;

	@Expose
	private Integer monthCount;

	@Expose
	private Integer yearCount;

	@Expose
	private Integer totalCount;

	@Expose
	private Integer pushCount;

	/**
	 * @return the dayCount
	 */
	public Integer getDayCount()
	{
		return dayCount;
	}

	/**
	 * @param dayCount
	 *            the dayCount to set
	 */
	public void setDayCount(Integer dayCount)
	{
		this.dayCount = dayCount;
	}

	/**
	 * @return the weekCount
	 */
	public Integer getWeekCount()
	{
		return weekCount;
	}

	/**
	 * @param weekCount
	 *            the weekCount to set
	 */
	public void setWeekCount(Integer weekCount)
	{
		this.weekCount = weekCount;
	}

	/**
	 * @return the monthCount
	 */
	public Integer getMonthCount()
	{
		return monthCount;
	}

	/**
	 * @param monthCount
	 *            the monthCount to set
	 */
	public void setMonthCount(Integer monthCount)
	{
		this.monthCount = monthCount;
	}

	/**
	 * @return the yearCount
	 */
	public Integer getYearCount()
	{
		return yearCount;
	}

	/**
	 * @param yearCount
	 *            the yearCount to set
	 */
	public void setYearCount(Integer yearCount)
	{
		this.yearCount = yearCount;
	}

	/**
	 * @return the totalCount
	 */
	public Integer getTotalCount()
	{
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(Integer totalCount)
	{
		this.totalCount = totalCount;
	}

	/**
	 * @return the pushCount
	 */
	public Integer getPushCount()
	{
		return pushCount;
	}

	/**
	 * @param pushCount
	 *            the pushCount to set
	 */
	public void setPushCount(Integer pushCount)
	{
		this.pushCount = pushCount;
	}
}
