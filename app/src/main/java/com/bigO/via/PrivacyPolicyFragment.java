package com.bigO.via;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PrivacyPolicyFragment extends Fragment {

    String policyIntro =  "Big O built the GRYD app as freemium app.This page is used to inform " +
            "visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our] Service. " +
            "If you choose to use our Service, then you agree to the collection and use of information in relation to this policy. " +
            "The Personal Information that We collect is used for providing and improving the Service. We will not use or share your " +
            "information with anyone except as described in this Privacy Policy. " +
            "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, " +
            "which is accessible at GRYD unless otherwise defined in this Privacy Policy.";

    String policyInfoCollection =  "For a better experience, while using our Service, We may require you to provide us with certain personally identifiable "
            + "information location The information that We request will be retained by us and used as described in this privacy policy. " +
            "The app does use third party services that may collect information used to identify you. " +
            "Link to privacy policy of third party service providers used by the app\n" +
            "Google Play Services\n" +
            "Firebase Analytics\n" +
            "Log Data\n" +
            "\n" +
            "We want to inform you that whenever you use our Service, in a case of an error in the app We collect data and information " +
            "(through third party products) on your phone called Log Data. This Log Data may include information such as your device " +
            "Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing our Service, " +
            "the time and date of your use of the Service, and other statistics.";

    String policyServiceProvider =  "We may employ third-party companies and individuals due to the following reasons:\n" +
            "To facilitate our Service;\n" +
            "To provide the Service on our behalf;\n" +
            "To perform Service-related services; or\n" +
            "To assist us in analyzing how our Service is used.\n" +
            "We want to inform users of this Service that these third parties have access to your Personal Information. " +
            "The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use " +
            "the information for any other purpose.";


    String policySecurity =  "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable " +
            "means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage " +
            "is 100% secure and reliable, and We cannot guarantee its absolute security.";

    String policyLinks =  "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. " +
            "Note that these external sites are not operated by [me/us]. Therefore, We strongly advise you to review the Privacy " +
            "Policy of these websites. We have no control over and assume no responsibility for the content, privacy policies, or " +
            "practices of any third-party sites or services.";

    String policyChange =  "We may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. " +
            "We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately " +
            "after they are posted on this page.";

    String policyContact =  "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at test@bigo.com.";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        TextView policyIntroView =  view.findViewById(R.id.policy_intro);
        TextView policyInfoCollectionView =  view.findViewById(R.id.policy_info_collection);
        TextView policyServiceProviderView =  view.findViewById(R.id.policy_service_provider);
        TextView policySecurityView =  view.findViewById(R.id.policy_security);
        TextView policyLinksView =  view.findViewById(R.id.policy_links);
        TextView policyChangeView =  view.findViewById(R.id.policy_change);
        TextView policyContactView =  view.findViewById(R.id.policy_contact);

        policyIntroView.setText(policyIntro);
        policyInfoCollectionView.setText(policyInfoCollection);
        policyServiceProviderView.setText(policyServiceProvider);
        policySecurityView.setText(policySecurity);
        policyLinksView.setText(policyLinks);
        policyChangeView.setText(policyChange);
        policyContactView.setText(policyContact);


        return view;
    }
}
