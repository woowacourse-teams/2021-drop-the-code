import { useState } from "react";

const useLocalStorage = <T>(key: string, initialValue: T): [T, (value: T) => void, () => void] => {
  const [localStorageValue, setLocalStorageValue] = useState(() => {
    try {
      const item = localStorage.getItem(key);

      return item ? (JSON.parse(item) as T) : initialValue;
    } catch (error) {
      return initialValue;
    }
  });

  const set = (value: T) => {
    try {
      localStorage.setItem(key, JSON.stringify(value));

      setLocalStorageValue(value);
    } catch (error) {
      console.error(error);

      setLocalStorageValue(initialValue);
    }
  };

  const remove = () => {
    localStorage.removeItem(key);

    setLocalStorageValue(initialValue);
  };

  return [localStorageValue, set, remove];
};

export default useLocalStorage;
