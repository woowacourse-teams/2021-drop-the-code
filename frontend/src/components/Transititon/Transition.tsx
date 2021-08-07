import { useEffect, useRef, useState } from "react";

import { CSSObject } from "styled-components";

interface ChildProps {
  css?: CSSObject;
  onClose: (callback: () => void) => void;
}

interface Props {
  initial?: CSSObject;
  animate?: CSSObject;
  exit?: CSSObject;
  duration: [number, number, number];
  children: (props: ChildProps) => JSX.Element;
}

const Transition = ({ initial, animate, exit, duration, children }: Props) => {
  const [status, setStatus] = useState<null | "initial" | "animate" | "exit" | "unmounted">(null);
  const [css, setCss] = useState<CSSObject>();

  const [initialDuration, animateDuration, exitDuration] = duration;

  const initialId = useRef<number>();
  const animationId = useRef<number>();

  const mounted = useRef<boolean>(false);

  const isMounted = mounted.current;

  useEffect(() => {
    if (!mounted.current) {
      mounted.current = true;
    }

    nextStep();
  }, [status]);

  useEffect(() => {
    return () => {
      window.clearTimeout(initialId.current);
      window.clearTimeout(animationId.current);
    };
  }, []);

  function nextStep() {
    if (!isMounted && status === null) {
      performMount();
    }

    if (status === "initial") {
      performInitial();
    }

    if (status === "animate") {
      performAnimation();
    }

    if (status === "exit") {
      performExit();
    }
  }

  function performMount() {
    setStatus("initial");
  }

  function performInitial() {
    if (initial) {
      initialId.current = window.setTimeout(() => {
        setCss(initial);
      }, 0);
    }

    animationId.current = window.setTimeout(() => {
      setStatus("animate");
    }, initialDuration);
  }

  function performAnimation() {
    if (animate) {
      setCss(animate);
    }

    animationId.current = window.setTimeout(() => {
      setStatus("exit");
    }, animateDuration);
  }

  function performExit(callback?: () => void) {
    if (exit) {
      setCss(exit);
    }

    animationId.current = window.setTimeout(() => {
      setStatus("unmounted");

      callback?.();
    }, exitDuration);
  }

  if (status === "unmounted") return null;

  return children({
    css,
    onClose: (callback) => {
      window.clearTimeout(animationId.current);
      performExit(callback);
    },
  });
};

export default Transition;
