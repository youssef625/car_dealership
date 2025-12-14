import { useEffect, useState } from "react";
import EditCarTable from "../../componantes/EditCarTable";

const EditCar = ({ cars, setCars }) => {
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
        console.log(data);
        setCars(data.content);
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

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Edit car</h2>
      <EditCarTable cars={cars} setCars={setCars} />
    </div>
  );
};

export default EditCar;
