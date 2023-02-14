import { useNavigate , useLocation } from 'react-router-dom'; 

const withRouter = WrappedComponent => props => {
  const location = useLocation();
  const navigate = useNavigate();
  // other hooks

  return (
    <WrappedComponent
      {...props}
      {...{ navigate, location }}
    />
  );
};

export default withRouter;