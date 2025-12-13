import Button from "react-bootstrap/Button";

const UsersTable = ({ users, fullUsers, setUsers }) => {
  function handleBanned(userp, banstate) {
    let newArr = fullUsers.map((user) => {
      if (user.id == userp.id) {
        return { ...user, banned: !banstate };
      }
      return user;
    });
    setUsers(newArr);
  }

  return (
    <div className="d-flex justify-content-center h-auto align-items-baseline ">
      <table className="table table-striped-columns w-75">
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {users.map((user, index) => (
            <tr key={index}>
              <th>{user.name}</th>
              <td>{user.email}</td>
              <td className="d-flex gap-2">
                <Button
                  onClick={() => handleBanned(user, user.banned)}
                  variant="warning"
                  disabled={user.banned}
                >
                  ban
                </Button>

                <Button
                  onClick={() => handleBanned(user, user.banned)}
                  variant="secondary"
                  disabled={!user.banned}
                >
                  unban
                </Button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UsersTable;
