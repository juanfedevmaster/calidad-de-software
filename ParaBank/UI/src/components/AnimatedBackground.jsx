import React from 'react';

export default function AnimatedBackground() {
  return (
    <div className="animated-bg" aria-hidden="true">
      <div className="animated-bg-gradient" />
      <svg
        className="animated-bg-lines"
        viewBox="0 0 1200 800"
        preserveAspectRatio="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          className="flow-line flow-line-1"
          d="M -100 620 C 150 560, 300 680, 500 600 C 700 520, 850 640, 1300 560"
        />
        <path
          className="flow-line flow-line-2"
          d="M -100 420 C 200 380, 350 460, 550 400 C 750 340, 900 420, 1300 360"
        />
        <path
          className="flow-line flow-line-3"
          d="M -100 220 C 180 260, 340 180, 540 230 C 740 280, 900 200, 1300 250"
        />
      </svg>
    </div>
  );
}
