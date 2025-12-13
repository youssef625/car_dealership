import Button from "react-bootstrap/Button";

const CarsTable = ({ cars,deleteCar }) => {
  return (
    <div className="d-flex justify-content-center vh-100 align-items-baseline">
      <table className="table table-striped-columns w-75">
        <thead className="">
          <tr className="">
            <th scope="col">Model</th>
            <th scope="col">Price</th>
            <th scope="col">Status</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          {cars.map((car, index) => {
            return (
              <tr key={index}>
                <th scope="row">{car.model}</th>
                <td>{car.price}</td>
                <td>{car.status}</td>
                <td>
                  {" "}
                  <Button variant="danger" onClick={() => deleteCar(car)}>
                    Delete
                  </Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default CarsTable;
