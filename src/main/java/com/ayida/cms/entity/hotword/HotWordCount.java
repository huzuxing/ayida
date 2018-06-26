package com.ayida.cms.entity.hotword;

public class HotWordCount extends BaseHotWord
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer dayCount;

	private Integer weekCount;

	private Integer monthCount;

	private Integer yearCount;

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

}
