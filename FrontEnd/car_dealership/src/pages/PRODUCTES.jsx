import React, { useEffect, useState } from "react";
import CAR_CARD from "../componantes/CAR_CARD";
import NAVBAR from "../componantes/NAVBAR";

const PRODUCTES = () => {
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");

  useEffect(() => {
    const fetchCars = async () => {
      try {
        const token = localStorage.getItem("token");

        const res = await fetch(
          `${import.meta.env.VITE_FINAL_BASE_URL}/api/cars`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const data = await res.json();
        setCars(data.content || []);
      } catch (err) {
        console.error("Error fetching cars:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchCars();
  }, []);

  if (loading) return <h2 className="text-center mt-5">Loading...</h2>;

  const filteredCars = cars.filter((car) =>
    `${car.make} ${car.model} ${car.year}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  return (
    <>
      <NAVBAR />

      <div className="container mt-5">
        {/* Search bar */}
        <div className="mb-4">
          <input
            type="text"
            className="form-control form-control-lg"
            placeholder="Search by make, model, or year..."
            value={search}
            onChange={(e) => setSearch(e.target.value)}
          />
        </div>

        {/* Cars Grid */}
        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-4">
          {filteredCars.length > 0 ? (
            filteredCars.map((car) => <CAR_CARD key={car.id} car={car} />)
          ) : (
            <p className="text-center">No cars found.</p>
          )}
        </div>
      </div>
    </>
  );
};

export default PRODUCTES;
