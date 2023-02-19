package fr.uparis.energy.model;

public class WifiComponent extends Component {

    private boolean isPoweredByAnother;

    @Override
    public boolean isPowered() {
        return super.isPowered() || isPoweredByAnother;
    }

    public void setPoweredByAnother(boolean state) {
        this.isPoweredByAnother = state;
    }
}
