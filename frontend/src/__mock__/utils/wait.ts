import { screen, waitForElementToBeRemoved } from "__mock__/utils/testUtils";

export const waitForLoadingToBeRemoved = async () => {
  try {
    await waitForElementToBeRemoved(await screen.findAllByLabelText("로딩중입니다."));
  } catch (error) {
    console.error(error.message);
  }
};
