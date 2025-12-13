import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./product-page.css";
import NAVBAR from "../componantes/NAVBAR";

const PRODUCT_PAGE = () => {
  const { id } = useParams();
  const [car, setCar] = useState(null);
  const [mainImage, setMainImage] = useState("");
  const [offerPrice, setOfferPrice] = useState("");
  const [lastOffer, setLastOffer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [sending, setSending] = useState(false);

  const API = "https://c0d4289b83ae.ngrok-free.app";

  useEffect(() => {
    const fetchCar = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You must login first.");
        setLoading(false);
        return;
      }

      try {
        // Fetch car details
        const res = await fetch(`${API}/api/cars/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (!res.ok) throw new Error("Car not found");
        const data = await res.json();
        setCar(data);
        setMainImage(data.images?.[0] || "");

        // Fetch last offer for this car by the user
        const offerRes = await fetch(`${API}/api/offers/${id}`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (offerRes.ok) {
          const offerData = await offerRes.json();
          setLastOffer(offerData.price || null);
        }
      } catch (err) {
        console.error("Error fetching data:", err);
      }
      setLoading(false);
    };

    fetchCar();
  }, [id]);

  const submitOffer = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      alert("You must login first.");
      return;
    }
    if (!offerPrice || offerPrice <= 0) {
      alert("Please enter a valid offer price.");
      return;
    }

    setSending(true);
    const offerData = { carId: Number(id), price: Number(offerPrice) };

    try {
      const res = await fetch(`${API}/api/offers`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(offerData),
      });
      if (!res.ok) throw new Error("Failed to send offer");

      alert("Offer submitted successfully!");
      setOfferPrice("");

      // Update last offer after submission
      setLastOffer(offerData.price);
    } catch (err) {
      console.error("Error submitting offer:", err);
      alert("Failed to submit offer. Try again.");
    }

    setSending(false);
  };

  if (loading) return <h2 className="text-center mt-5">Loading...</h2>;
  if (!car) return <h2 className="text-center mt-5">Car Not Found</h2>;

  return (
    <>
      <NAVBAR />

      <div className="full-width-page">
        <div className="container-fluid px-5 mt-5">
          <div className="row g-4">

            {/* Product Images */}
            <div className="col-md-6">
              <img src={mainImage} alt="Car" className="product-image mb-3" />
              <div className="thumbnail-wrapper">
                {car.images?.map((img, index) => (
                  <img
                    key={index}
                    src={img}
                    alt={`Thumbnail ${index}`}
                    className={`thumbnail rounded ${mainImage === img ? "active" : ""}`}
                    onClick={() => setMainImage(img)}
                  />
                ))}
              </div>
            </div>

            {/* Car Details */}
            <div className="col-md-6 d-flex flex-column justify-content-start">
              <h2 className="mb-3">{car.make} {car.model} ({car.year})</h2>

              <p className="text-muted mb-2">
                Status:{" "}
                <span className={car.status === "AVAILABLE" ? "text-success" : "text-danger fw-bold"}>
                  {car.status}
                </span>
              </p>

              <div className="mb-3">
                <span className="h4 me-2">${car.price.toLocaleString()}</span>
              </div>

              {/* Description from API */}
              <p className="car-description">
                {car.description || "No description available."}
              </p>

              {/* Offer Input */}
              <div className="mb-3">
                <label className="form-label fw-bold">Make an Offer:</label>
                <input
                  type="number"
                  className="form-control form-control-lg"
                  placeholder="Enter your offer price"
                  value={offerPrice}
                  onChange={(e) => setOfferPrice(e.target.value)}
                />
              </div>

              {/* Submit Offer Button */}
              <button
                className="btn btn-primary btn-lg w-100 mb-2"
                onClick={submitOffer}
                disabled={sending}
              >
                <i className="bi bi-cart-plus"></i>{" "}
                {sending ? "Submitting..." : "Submit Offer"}
              </button>

              {/* Last Offer */}
              {lastOffer !== null && (
                <p className="text-muted mt-2">
                  Your last offer: <strong>${lastOffer.toLocaleString()}</strong>
                </p>
              )}

            </div>

          </div>
        </div>
      </div>
    </>
  );
};

export default PRODUCT_PAGE;
