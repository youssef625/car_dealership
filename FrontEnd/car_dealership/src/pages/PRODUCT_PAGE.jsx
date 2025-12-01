import React, { useState } from "react";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./product-page.css";
import NAVBAR from "../componantes/NAVBAR";

const PRODUCT_PAGE = () => {
  const car = {
    id: 1,
    make: "Tesla",
    model: "Model 3",
    year: 2022,
    price: 48000,
    description:
      "A premium electric sedan offering long range, fast acceleration, and advanced driver assistance technology.",
    status: "AVAILABLE",
    images: [
      "https://tesla-cdn.thron.com/delivery/public/image/tesla/4a5710e9-41d8-4bbe-a20e-b5a03ef64a68/bvlatuR/std/4096x2560/Model-3-Main-Hero-Desktop-LHD",
      "https://tesla-cdn.thron.com/delivery/public/image/tesla/812b8890-b006-4a66-a907-6fba88028a42/bvlatuR/std/2880x1800/Model-3-Interior-Hero-Desktop",
      "https://tesla-cdn.thron.com/delivery/public/image/tesla/94a123da-2338-4e7e-be40-20ec594c45e0/bvlatuR/std/2880x1800/Model-3-Range-Hero-Desktop",
    ],
  };

  const [mainImage, setMainImage] = useState(car.images[0]);
  const [offerPrice, setOfferPrice] = useState("");

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
                  className={`thumbnail rounded ${
                    mainImage === img ? "active" : ""
                  }`}
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

            {/* Removed Reviews/Stars */}

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
            <button className="btn btn-primary btn-lg w-100">
              <i className="bi bi-cart-plus"></i> Submit Offer
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
