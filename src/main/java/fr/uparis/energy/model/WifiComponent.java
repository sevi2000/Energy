package fr.uparis.energy.model;

public class WifiComponent extends Component {

    private boolean isPoweredByAnOther;
    @Override
    public boolean isPowered() {
        return super.isPowered() || isPoweredByAnOther;
    }
}
