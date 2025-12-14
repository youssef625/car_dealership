
import Button from 'react-bootstrap/Button';

const RequestsTable = ({ requests,handleRequests}) => {

  return (
    <div className="d-flex justify-content-center vh-100 align-items-baseline">
      <table className="table table-striped-columns w-75">
        <thead className="">
          <tr className="">
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Action</th>
          </tr>
        </thead>
        <tbody>
          {requests.map((request, index) => {
            return (
              <tr key={index}>
                <th scope="row">{request.name}</th>
                <td>{request.email}</td>
                <td>
                  {" "}
                  <Button  variant="success" onClick={()=>handleRequests(request)}>Accept</Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>

    // <div className="d-flex justify-content-center">
    //   <Card className="p-1 mb-3 bg-secondary text-dark ">
    //     <Card.Header className="fs-1 text fw-bold">{name}</Card.Header>
    //     <Card.Body>
    //       <Card.Title>{email}</Card.Title>
    //       <Card.Text>
    //         Kindly review my details and approve my request to gain access to
    //         the administration system
    //       </Card.Text>
    //       <Button variant="success">Accept</Button>
    //     </Card.Body>
    //   </Card>
    // </div>
  );
};

export default RequestsTable;
