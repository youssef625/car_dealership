import React from "react";
import { Link } from "react-router-dom";

const CAR_CARD = ({ car }) => {
  return (
    <div className="col-12 col-md-6 col-lg-4">
      <div className="card shadow-sm h-100 d-flex flex-column">

        {/* Image */}
        <div style={{ width: "100%", overflow: "hidden" }}>
          <img
            src={car.images[0]}
            alt={car.make}
            style={{
              width: "100%",
              height: "225px",
              objectFit: "cover",
              display: "block"
            }}
          />
        </div>

        {/* Card body */}
        <div className="card-body d-flex flex-column flex-grow-1">
          <h5 className="card-title">
            {car.make} {car.model}
          </h5>
          <p className="card-text text-muted flex-grow-1">
            {car.year} • ${car.price.toLocaleString()}
          </p>

          {/* Button always at bottom */}
          <Link to={`/car/${car.id}`} className="btn btn-primary w-100 mt-auto">
            View
          </Link>
        </div>
      </div>
    </div>
  );
};

export default CAR_CARD;
