import { useState } from "react";

const useLocalStorage = <T>(key: string, initialValue: T) => {
  const [localStorageValue, setLocalStorageValue] = useState(() => {
    try {
      const item = localStorage.getItem(key);

      return item ? JSON.parse(item) : initialValue;
    } catch (error) {
      return initialValue;
    }
  });

  const set = (value: T) => {
    try {
      localStorage.setItem(key, JSON.stringify(value));

      setLocalStorageValue(JSON.stringify(value));
    } catch (error) {
      console.error(error);

      setLocalStorageValue(initialValue);
    }
  };

  return [localStorageValue, set];
};

export default useLocalStorage;
