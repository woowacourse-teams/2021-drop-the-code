import { screen, waitForElementToBeRemoved } from "__mock__/utils/testUtils";

export const waitForLoadingToBeRemoved = async () => {
  await waitForElementToBeRemoved(await screen.findAllByLabelText("로딩중입니다."));
};
