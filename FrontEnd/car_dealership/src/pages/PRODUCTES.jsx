import React, { useEffect, useState } from "react";
import CAR_CARD from "../componantes/CAR_CARD";
import NAVBAR from "../componantes/NAVBAR";

const PRODUCTES = () => {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/cars");
        const data = await res.json();

        setCars(data);
        setLoading(false);
      } catch (err) {
        console.error("Error fetching cars:", err);
        setLoading(false);
      }
    };

    fetchCars();
  }, []);

  if (loading) return <h2 className="text-center mt-5">Loading...</h2>;

  return (
    <>
      <NAVBAR />
      <div className="container mt-5">
        <div className="row g-4">
          {cars.map((car) => (
            <CAR_CARD key={car.id} car={car} />
          ))}
        </div>
      </div>
    </>
  );
};

export default PRODUCTES;
