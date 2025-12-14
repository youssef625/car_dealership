import { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";

const OffersTable = ({ offers, setOffers, fetchOffers }) => {
  const [role, setRole] = useState(""); // هنا هنخزن الدور
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const validateToken = async () => {
      const token = localStorage.getItem("token");
      if (!token) {
        alert("You must login first.");
        setLoading(false);
        return;
      }

      try {
        const res = await fetch(
          `${import.meta.env.VITE_FINAL_BASE_URL}/api/auth/validate`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!res.ok) throw new Error("Token validation failed");

        const data = await res.json();
        console.log("Validation response:", data);

        if (data.valid) {
          setRole(data.role);
        } else {
          alert("Invalid token");
        }
      } catch (error) {
        console.error("Validation error:", error);
        alert("Error validating token");
      } finally {
        setLoading(false);
      }
    };

    validateToken();
  }, []);

  const handleApproveButton = async (offer) => {
    const token = localStorage.getItem("token");
    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/offers/admin/approve/${
          offer.id
        }`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error();
      await fetchOffers();
      alert("Offer approved");
    } catch {
      alert("Error approving offer");
    }
  };

  const handleDeclineButton = async (offer) => {
    const token = localStorage.getItem("token");
    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/offers/admin/cancel/${
          offer.id
        }`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error();
      await fetchOffers();
      alert("Offer declined");
    } catch {
      alert("Error declining offer");
    }
  };

  const handleConfirmButton = async (offer) => {
    const token = localStorage.getItem("token");
    try {
      const res = await fetch(
        `${import.meta.env.VITE_FINAL_BASE_URL}/api/offers/admin/confirm/${
          offer.id
        }`,
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (!res.ok) throw new Error();
      await fetchOffers();
      alert("Offer confirmed");
    } catch {
      alert("Error confirming offer");
    }
  };

  return (
    <div className="d-flex justify-content-center vh-100 align-items-baseline ">
      <table className="table table-striped-columns w-75">
        <thead className="">
          <tr className="">
            <th scope="col">Car</th>
            <th scope="col">Model</th>
            <th scope="col">Status</th>
            <th scope="col">Last Offer</th>

            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          {offers.map((offer, index) => {
            return (
              <tr key={index}>
                <th scope="row">{offer.make}</th>
                <td>{offer.model}</td>
                <td>{offer.status}</td>
                <td>{offer.lastOffer}</td>

                <td className="d-flex gap-3">
                  {/* APPROVE → لأي role لو status = AVAILABLE */}
                  {offer.status === "AVAILABLE" && offer.lastOffer > 0 && (
                    <Button
                      variant="success"
                      onClick={() => handleApproveButton(offer)}
                    >
                      Approve
                    </Button>
                  )}

                  {/* CONFIRM + DECLINE → superAdmin بس لو status = RESERVED */}
                  {offer.status === "RESERVED" && role === "superAdmin" && offer.lastOffer > 0 && (
                    <>
                      <Button
                        variant="success"
                        onClick={() => handleConfirmButton(offer)}
                      >
                        Confirm
                      </Button>

                      <Button
                        variant="danger"
                        onClick={() => handleDeclineButton(offer)}
                      >
                        Decline
                      </Button>
                    </>
                  )}
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default OffersTable;
