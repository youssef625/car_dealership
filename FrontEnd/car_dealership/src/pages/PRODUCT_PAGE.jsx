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
  const [loading, setLoading] = useState(true);
  const [sending, setSending] = useState(false);

  useEffect(() => {
    const fetchCar = async () => {
      try {
        const res = await fetch(`http://localhost:8080/api/cars/${id}`);
        const data = await res.json();

        setCar(data);
        setMainImage(data.images[0]);
        setLoading(false);
      } catch (err) {
        console.error("Error fetching car:", err);
        setLoading(false);
      }
    };

    fetchCar();
  }, [id]);


  const submitOffer = async () => {
    if (!offerPrice || offerPrice <= 0) {
      alert("Please enter a valid offer price.");
      return;
    }

    setSending(true);

    const offerData = {
      carId: Number(id),
      userId: 1,          
      price: Number(offerPrice),
      employeeId: 0       
    };

    try {
      const res = await fetch("http://localhost:8080/api/offers", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(offerData)
      });

      if (!res.ok) throw new Error("Failed to send offer");

      alert("Offer submitted successfully!");

      setOfferPrice("");
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

      <div className="container mt-5">
        <div className="row">

          {/* Product Images */}
          <div className="col-md-6 mb-4">
            <img
              src={mainImage}
              alt="Car"
              className="img-fluid rounded mb-3 product-image"
            />

            <div className="d-flex justify-content-between">
              {car.images.map((img, index) => (
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
          <div className="col-md-6">
            <h2 className="mb-3">
              {car.make} {car.model} ({car.year})
            </h2>

            <p className="text-muted mb-2">
              Status:{" "}
              <span
                className={
                  car.status === "AVAILABLE"
                    ? "text-success"
                    : "text-danger fw-bold"
                }
              >
                {car.status}
              </span>
            </p>

            <div className="mb-3">
              <span className="h4 me-2">${car.price.toLocaleString()}</span>
            </div>

            <p className="mb-4">{car.description}</p>

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
              className="btn btn-primary btn-lg w-100"
              onClick={submitOffer}
              disabled={sending}
            >
              <i className="bi bi-cart-plus"></i>{" "}
              {sending ? "Submitting..." : "Submit Offer"}
            </button>

            {/* Features */}
            <div className="mt-4">
              <h5>Key Features:</h5>
              <ul>
                <li>Electric Motor</li>
                <li>Autopilot Capability</li>
                <li>Premium Interior</li>
                <li>Fast Charging Support</li>
              </ul>
            </div>

          </div>
        </div>
      </div>
    </>
  );
};

export default PRODUCT_PAGE;
