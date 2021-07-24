import { SelectHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR } from "utils/constants/color";

export type Props = SelectHTMLAttributes<HTMLSelectElement>;

const Inner = styled.select`
  width: 150px;
  padding: 5px 40px 5px 5px;
  border: 1px solid ${COLOR.GRAY_200};
  border-radius: 4px;
`;

const Select = (props: Props) => <Inner {...props} />;

export default Select;
