import React from 'react';
import CAR_CARD from '../componantes/CAR_CARD';

const PRODUCTES = () => {
  const cars = Array.from({ length: 5 });

  return (
    <>
      {cars.map((_, index) => (
        <CAR_CARD key={index} />
      ))}
    </>
  );
};

export default PRODUCTES;
