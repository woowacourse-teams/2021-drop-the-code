export const toURLSearchParams = (options: Record<string, unknown>) => {
  return (
    "?" +
    Object.entries(options)
      .filter(([_, value]) => Boolean(value))
      .map(([key, value]) => {
        return Array.isArray(value) ? value.map((value) => `${key}=${value}`).join("&") : `${key}=${value}`;
      })
      .filter(Boolean)
      .join("&")
  );
};

export const toPassedTimeString = (year: number, month: number, date: number) => {
  const currentDate = new Date();

  const [cy, cm, cd] = [currentDate.getFullYear(), currentDate.getMonth() + 1, currentDate.getDate()];

  const totalDates = 365 * year + 30 * month + date;
  const totalCurrentDates = 365 * cy + 30 * cm + cd;

  const totalPassedDates = totalCurrentDates - totalDates;

  if (totalPassedDates === 0) return "오늘";
  if (0 < totalPassedDates && totalPassedDates < 7) return `${totalPassedDates}일 전`;
  if (7 <= totalPassedDates && totalPassedDates < 30) return `${Math.floor(totalPassedDates / 7)}주 전`;
  if (30 <= totalPassedDates && totalPassedDates < 365) return `${Math.floor(totalPassedDates / 30)}개월 전`;
  if (365 < totalPassedDates) return `${Math.floor(totalPassedDates / 365)}년 전`;
};

export const removeMillisecond = (time: string) => {
  return time.slice(0, -4);
};

export const formatTimeToPassedTime = (time: Date) => {
  const year = time.getFullYear();
  const month = time.getMonth() + 1;
  const date = time.getDate();

  return toPassedTimeString(year, month, date);
};
