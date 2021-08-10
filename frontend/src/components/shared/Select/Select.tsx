import { SelectHTMLAttributes } from "react";

import styled from "styled-components";

import { COLOR } from "utils/constants/color";

export type Props = SelectHTMLAttributes<HTMLSelectElement>;

const Inner = styled.select`
  width: 9.375rem;
  padding: 0.3125rem 2.5rem 0.3125rem 0.3125rem;
  border: 1px solid ${COLOR.GRAY_200};
  border-radius: ${({ theme }) => theme.common.shape.rounded};
`;

const Select = (props: Props) => <Inner {...props} />;

export default Select;
