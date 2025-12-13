import Button from "react-bootstrap/Button";

const OffersTable = ({ offers, setOffers }) => {
  function handleReserveButtom(e, offerp) {
    e.target.classList.toggle("d-none");
    let newArr = offers.map((offer) => {
      if (offer.id == offerp.id) {
        return { ...offer, status: "reserved" };
      }
      return offer;
    });
    setOffers(newArr);
    //fetch offers with new state for back end
  }
  function handleConfirmButtom(offerp) {
    let newArr = offers.map((offer) => {
      if (offer.id == offerp.id) {
        return { ...offer, status: "booked" };
      }
      return offer;
    });
    setOffers(newArr);
    //fetch offers with new state for back end
  }
  // function handleDeclineButton(offerp) {

  //   const finalArr = offers
  //     .filter((offer) => offer.id === offerp.id) 
  //     .map((offer) => ({ ...offer, status: "available" }));

  //   const newArr = offers.filter((offer) => offer.id !== offerp.id);

  //   setOffers(newArr);

  //   console.log("Offer to send to backend:", finalArr);
  // }
  function handleDeclineButton(offerp) {
    //updated version for offers
    const updatedOffers = offers.map((offer) => {
      if (offer.id === offerp.id) {
        return { ...offer, status: "available" };
      }
      return offer;
    });

    //Array with all offers and updated offer
    console.log("Offers with updated status:", updatedOffers);

    //singleOffer if send to backend
    const finalArr = updatedOffers.filter((offer) => offer.id === offerp.id);

    //remove
    const newArr = updatedOffers.filter((offer) => offer.id !== offerp.id);

    //send offers to backend after removal
    console.log("Offers after removing the updated offer:", newArr);

    //for display
    setOffers(newArr);
  }

  return (
    <div className="d-flex justify-content-center vh-100 align-items-baseline ">
      <table className="table table-striped-columns w-75">
        <thead className="">
          <tr className="">
            <th scope="col">Customer name</th>
            <th scope="col">Car</th>
            <th scope="col">Price</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          {offers.map((offer, index) => {
            return (
              <tr key={index}>
                <th scope="row">{offer.cusName}</th>
                <td>{offer.car}</td>
                <td>{offer.price + "$"}</td>
                <td>{offer.status}</td>
                {offer.status == "available" && (
                  <td>
                    <Button
                      variant="warning"
                      onClick={(e) => {
                        handleReserveButtom(e, offer);
                      }}
                    >
                      Reserve
                    </Button>
                  </td>
                )}
                {(offer.status == "reserved" ||
                  offer.status == "booked" ||
                  offer.status == "canceled") && (
                  <td className="d-flex gap-3">
                    <Button
                      variant="success"
                      onClick={() => {
                        handleConfirmButtom(offer);
                      }}
                    >
                      confirm
                    </Button>
                    <Button
                      variant="danger"
                      onClick={() => {
                        handleDeclineButton(offer);
                      }}
                    >
                      decline
                    </Button>
                  </td>
                )}
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default OffersTable;
