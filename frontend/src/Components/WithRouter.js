import { useLocation, useNavigate } from 'react-router-dom'; 

const withRouter = WrappedComponent => props => {
  const location = useLocation();
  const navigate = useNavigate();
  // other hooks

  return (
    <WrappedComponent
      {...props}
      {...{ location, navigate }}
    />
  );
};

export default withRouter;