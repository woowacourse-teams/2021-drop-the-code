export const toURLSearchParams = (options: Record<string, unknown>) => {
  return (
    "?" +
    Object.entries(options)
      .filter(([_, value]) => value !== null)
      .map(([key, value]) => {
        return Array.isArray(value) ? value.map((value) => `${key}=${value}`).join("&") : `${key}=${value}`;
      })
      .filter(Boolean)
      .join("&")
  );
};
