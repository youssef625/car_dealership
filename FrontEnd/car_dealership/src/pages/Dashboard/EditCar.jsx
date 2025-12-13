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

    const headers = {
      Authorization: `Bearer ${token}`,
    };

    try {
      const res = await fetch(`${API}/api/cars/${id}`, { headers });
      if (!res.ok) throw new Error("Car not found");

      const data = await res.json();
      setCar(data);
      setMainImage(data.images?.[0] || "");

      const offerRes = await fetch(`${API}/api/offers/${id}`, { headers });
      if (offerRes.ok) {
        const offerData = await offerRes.json();
        setLastOffer(offerData.price ?? null);
      }
    } catch (err) {
      console.error("Error fetching data:", err);
    } finally {
      setLoading(false);
    }
  };

  fetchCar();
}, []);


  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Edit car</h2>
      <EditCarTable cars={cars} setCars={setCars} />
    </div>
  );
};

export default EditCar;
