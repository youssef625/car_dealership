import { useEffect, useState } from "react";
import OffersTable from "../../componantes/OffersTable";

const Offers = () => {
  const [loading, setLoading] = useState(true);
  const [offers, setOffers] = useState([]);
  const fetchOffers = async () => {
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
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      if (!res.ok) throw new Error("Offers not found");

      const data = await res.json();
      setOffers(data.content);
    } catch (err) {
      console.error("Error fetching offers:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchOffers();
  }, []);

  if (loading) {
    return (
      <div className="text-center mt-5 text-danger">
        <p>Loading offers...</p>
      </div>
    );
  }

  return (
    <div className="mt-5">
      <h2 className="text-center text-danger">Offers</h2>
      <OffersTable
        offers={offers}
        setOffers={setOffers}
        fetchOffers={fetchOffers}
      />
    </div>
  );
};

export default Offers;
