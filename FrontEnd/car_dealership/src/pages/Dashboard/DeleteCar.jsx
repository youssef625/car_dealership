import React, { useEffect, useState } from "react";
import CarsTable from "../../componantes/CarsTable";

const DeleteCar = () => {
  const [listCars, setListCars] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCar = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You must login first.");
        setLoading(false);
        return;
      }
      try {
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
        if (!res.ok) throw new Error("Car not found");

        const data = await res.json();
        setListCars(data.content);
      } catch (err) {
        console.error("Error fetching data:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchCar();
  }, []);
  if (loading) {
    return (
      <div className="text-center mt-5 text-danger">
        <p>Loading cars...</p>
      </div>
    );
  }

  async function deleteCar(car) {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must login first.");
      return;
    }

    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/cars/${car.id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) throw new Error("Failed to delete car");
      setListCars((prev) => prev.filter((c) => c.id !== car.id));
      alert("Car deleted");
    } catch (error) {
      console.error("Delete error:", error);
      alert("Error deleting car");
    }
  }

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Delete car</h2>

      <CarsTable listCars={listCars} deleteCar={deleteCar} />
    </div>
  );
};

export default DeleteCar;
